package ru.ekrem.financialliteracy.service;


import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.ConfirmCodeDto;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.security.JwtResponse;

public interface RegistrationService {
    JwtResponse setPhone(String phone);
    boolean confirmPhonePassword(ConfirmCodeDto dto, Long userId);
    boolean setAdditional(AdditionalUserInformationDto dto,Long userId);

    JwtResponse setPassword(PasswordDto dto,Long userId);
}
