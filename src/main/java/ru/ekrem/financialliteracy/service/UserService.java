package ru.ekrem.financialliteracy.service;

import ru.ekrem.financialliteracy.entity.User;

public interface UserService {
    User getByPhone(String phone);
    User setRefreshToken(String phone,String refreshToken);
    User createAnonymous(String phone);
}
