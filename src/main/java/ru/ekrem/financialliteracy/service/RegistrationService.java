package ru.ekrem.financialliteracy.service;


import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.ConfirmCodeDto;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.dto.auth.JwtDto;

public interface RegistrationService {
    JwtDto setPhone(String phone);
    JwtDto confirmPhonePassword(ConfirmCodeDto dto, Long userId);
    boolean setAdditional(AdditionalUserInformationDto dto,Long userId);

    JwtDto setPassword(PasswordDto dto, Long userId);
}
