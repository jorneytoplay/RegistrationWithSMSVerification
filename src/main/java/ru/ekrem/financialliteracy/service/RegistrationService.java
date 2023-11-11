package ru.ekrem.financialliteracy.service;


import ru.ekrem.financialliteracy.dto.LoginDto;
import ru.ekrem.financialliteracy.security.JwtResponse;

public interface RegistrationService {
    JwtResponse setPhone(String phone);
    boolean confirmPhonePassword(LoginDto dto);
}
