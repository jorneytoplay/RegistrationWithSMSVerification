package ru.ekrem.financialliteracy.service.impl;

import ru.ekrem.financialliteracy.dao.PhoneSmsDAO;
import ru.ekrem.financialliteracy.dao.RegistrationDAO;

import ru.ekrem.financialliteracy.dto.LoginDto;
import ru.ekrem.financialliteracy.dto.registration.PhoneSmsDto;
import ru.ekrem.financialliteracy.entity.PhoneSms;
import ru.ekrem.financialliteracy.entity.RegistrationUser;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ekrem.financialliteracy.entity.User;
import ru.ekrem.financialliteracy.security.AuthService;
import ru.ekrem.financialliteracy.security.JwtResponse;
import ru.ekrem.financialliteracy.service.RegistrationService;
import ru.ekrem.financialliteracy.service.SmsService;
import ru.ekrem.financialliteracy.service.UserService;
import ru.ekrem.financialliteracy.util.GeneratorUtil;

import javax.transaction.Transactional;
import java.util.Arrays;

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


    @Transactional(rollbackOn = Exception.class)
    @Override
    public JwtResponse setPhone(String phone) {
        if(userService.getByPhone(phone)==null) {
            //throw new AuthenticationException("Вы уже подтвердили номер телефона");
        }
        User user = userService.createAnonymous(phone);
        PhoneSms result = phoneSmsDAO.save(
                PhoneSms.builder()
                        .userId(user.getId())
                        .code(Long.valueOf(GeneratorUtil.getRandomNumber(5))) //Генерируем 5-ти разрядное число
                        .createDate(GeneratorUtil.getTimestamp())
                        .build()
        );

        System.out.println(Arrays.toString(smsService.sendSms(
                user.getPhone(),
                "Код подверждения: " + result.getCode(),
                0,
                3)));

        registrationDAO.save(
                RegistrationUser.builder()
                        .userId(user.getId())
                        .registration_step(1L)
                        .build()
        );

        JwtResponse response = authService.registration(user);
        return response;
    }

    @Override
    public boolean confirmPhonePassword(LoginDto dto) {
        return false;
    }

    @Transactional
    public boolean confirmPhone(PhoneSmsDto dto) {

        if(phoneSmsDAO.findByUserIdAndCode(1L, dto.getCode()) != null){

        }
        return true;
    }
}
