package ru.ekrem.financialliteracy.service;

import ru.ekrem.financialliteracy.dto.registration.NicknameDTO;

public interface RegistrationService {
    boolean indicateName(NicknameDTO nicknameDTO);
}
