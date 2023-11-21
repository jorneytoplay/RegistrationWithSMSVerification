package ru.ekrem.financialliteracy.service.impl;

import javax.transaction.Transactional;
import ru.ekrem.financialliteracy.dao.PhoneSmsDAO;
import ru.ekrem.financialliteracy.dao.RegistrationDAO;

import ru.ekrem.financialliteracy.dto.auth.LoginDto;
import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.ConfirmCodeDto;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.entity.PhoneSms;
import ru.ekrem.financialliteracy.entity.RegistrationUser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ekrem.financialliteracy.entity.User;
import ru.ekrem.financialliteracy.handler.exception.NotFoundException;
import ru.ekrem.financialliteracy.handler.exception.SqlOperationException;
import ru.ekrem.financialliteracy.security.AuthService;
import ru.ekrem.financialliteracy.dto.auth.JwtDto;
import ru.ekrem.financialliteracy.service.RegistrationService;
import ru.ekrem.financialliteracy.service.SmsService;
import ru.ekrem.financialliteracy.service.UserService;
import ru.ekrem.financialliteracy.util.GeneratorUtil;
import ru.ekrem.financialliteracy.util.RegistrationValidator;


@Service
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private RegistrationDAO registrationDAO;
    @Autowired
    private PhoneSmsDAO phoneSmsDAO;
    @Autowired
    private SmsService smsService;
    @Autowired
    private UserService userService;

    @Autowired
    private AuthService authService;

    @Autowired
    private RegistrationValidator registrationValidator;


    @Transactional(rollbackOn = Exception.class)
    @Override
    public JwtDto setPhone(String phone) {
        // Пытаемся достать пользователя
        User user = userService.getByPhone(phone);

        // Если пользователя нет, то создаем нового
        if(user==null){

            user = userService.createAnonymous(phone);

            //Создаем запись об регистририрующемся пользователе
            registrationDAO.save(
                    RegistrationUser.builder()
                            .userId(user.getId())
                            .registration_step(1L)
                            .build()
            );
        }

        // Создаем в базе данных запись с пользователем и отправленным ему смс-кодом
        PhoneSms result = phoneSmsDAO.save(
                PhoneSms.builder()
                        .userId(user.getId())
                        .code(Long.valueOf(GeneratorUtil.getRandomNumber(5))) //Генерируем 5-ти разрядное число
                        .createDate(GeneratorUtil.getTimestamp())
                        .build()
        );

        //Отправляем запрос в Smsc на отправку сгенерированного смс-кода
        smsService.sendSms(user.getPhone(), "Код подверждения: " + result.getCode(), 0, 3);


        //Возвращаем jwt с claim-ом phoneVerified=false для продолжения регистрации
        return authService.registration(user, false);
    }

    @Transactional
    @Override
    public JwtDto confirmPhonePassword(ConfirmCodeDto dto, Long userId) {
        //Достаем пользователя
        User user = userService.getById(userId);

        RegistrationUser registrationUser = registrationDAO.getByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("Запись об регистрации пользователя не найдена"));

        //Удаляем запись код и номер телефона
        if(phoneSmsDAO.deleteByUserIdAndCode(userId, dto.getCode())<=0){
            throw new SqlOperationException("Ошибка удаления записи c смс кодом");
        }

        //Увеличиваем шаг регистрации
        if(registrationUser.getRegistration_step()==1)
            registrationDAO.updateStepByUserId(userId,2L);
        //Возвращаем jwt с claim-ом phoneVerified=true для продолжения регистрации
        return authService.registration(user, true);
    }

    @Transactional
    @Override
    public boolean setAdditional(AdditionalUserInformationDto dto,Long userId) {
        //Достаем пользователя
        User user = userService.getById(userId);

        RegistrationUser registrationUser = registrationDAO.getByUserId(user.getId())
                .orElseThrow(() -> new NotFoundException("Запись об регистрации пользователя не найдена"));

        registrationValidator.validateStep(registrationUser.getRegistration_step(),2L );
        //Устанавливаем дополнительную инофрмацию
        userService.setAdditional(dto,userId);
        registrationDAO.updateStepByUserId(userId,3L);
        return true;
    }

    @Transactional
    @Override
    public JwtDto setPassword(PasswordDto dto, Long userId) {

        RegistrationUser registrationUser = registrationDAO.getByUserId(userId)
                .orElseThrow(() -> new NotFoundException("Регистрация не обнаружена"));
        registrationValidator.validateStep(registrationUser.getRegistration_step(),3L );

        //Обновляем шаг
        registrationDAO.updateStepByUserId(userId,4L);
        //Устанавливаем пароль
        User user = userService.setPassword(dto,userId);

        //Возвращаем jwt клиента с access и refresh токенами
        return authService.login(new LoginDto(user.getPhone(),user.getPassword()));
    }
}
