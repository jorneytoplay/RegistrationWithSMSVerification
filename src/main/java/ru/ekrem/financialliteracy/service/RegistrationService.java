package ru.ekrem.financialliteracy.service;


import ru.ekrem.financialliteracy.dto.registration.PhoneSmsDto;

public interface RegistrationService {
    boolean setPhone(String phone);
    boolean confirmPhone(PhoneSmsDto dto);
}
