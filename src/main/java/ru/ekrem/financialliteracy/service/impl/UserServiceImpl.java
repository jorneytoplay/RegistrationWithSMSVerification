package ru.ekrem.financialliteracy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.ekrem.financialliteracy.dao.UserDAO;
import ru.ekrem.financialliteracy.entity.Role;
import ru.ekrem.financialliteracy.entity.User;
import ru.ekrem.financialliteracy.service.UserService;
import ru.ekrem.financialliteracy.util.RoleEnum;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Override
    public User getByPhone(String phone) {
        return userDAO.getByPhone(phone);
    }

    @Override
    public User setRefreshToken(String phone, String refreshToken) {
        return userDAO.setRefreshToken(phone,refreshToken);
    }

    @Override
    public User createAnonymous(String phone) {
        User user = User.builder()
                .phone(phone)
                .role(new Role(3L,"UNREGISTERED"))
                .build();
        return userDAO.save(user);
    }
}
