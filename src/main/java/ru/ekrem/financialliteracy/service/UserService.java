package ru.ekrem.financialliteracy.service;

import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.entity.User;

public interface UserService {
    User getByPhone(String phone);
    User setRefreshToken(String phone,String refreshToken);
    User createAnonymous(String phone);

    boolean setAdditional(AdditionalUserInformationDto dto,Long userId);

    User setPassword(PasswordDto dto, Long userId);

    boolean setRole(Long roleId,User user);
    User getById(Long userId);
}
