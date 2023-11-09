package ru.ekrem.financialliteracy.service.impl;

import ru.ekrem.financialliteracy.dao.RegistrationDAO;
import ru.ekrem.financialliteracy.dao.UserDAO;
import ru.ekrem.financialliteracy.dto.registration.NicknameDTO;
import ru.ekrem.financialliteracy.entity.RegistrationUser;
import ru.ekrem.financialliteracy.entity.User;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ekrem.financialliteracy.service.RegistrationService;

import javax.transaction.Transactional;

@Service
@AllArgsConstructor
public class RegistrationServiceImpl implements RegistrationService {

    @Autowired
    private final RegistrationDAO registrationDAO;


    @Transactional
    @Override
    public boolean indicateName(NicknameDTO nicknameDTO) {
        if(nicknameDTO!=null){
            RegistrationUser registrationUser = RegistrationUser
                    .builder()
                    .nickname(nicknameDTO.getNickname())
                    .registration_step(1L)
                    .build();
            registrationDAO.save(registrationUser);
            return true;
        }
        return false;
    }
}
