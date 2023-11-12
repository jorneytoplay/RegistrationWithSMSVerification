package ru.ekrem.financialliteracy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ekrem.financialliteracy.dao.RoleDAO;
import ru.ekrem.financialliteracy.dao.UserDAO;
import ru.ekrem.financialliteracy.dto.LoginDto;
import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.entity.Role;
import ru.ekrem.financialliteracy.entity.User;
import ru.ekrem.financialliteracy.security.AuthService;
import ru.ekrem.financialliteracy.security.JwtResponse;
import ru.ekrem.financialliteracy.service.UserService;
import ru.ekrem.financialliteracy.util.RoleEnum;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDAO userDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public User getByPhone(String phone) {
        return userDAO.getByPhone(phone);
    }

    @Override
    public User setRefreshToken(String phone, String refreshToken) {
        userDAO.setRefreshToken(phone,refreshToken);
        return userDAO.getByPhone(phone);
    }

    @Override
    public User createAnonymous(String phone) {
        User user = User.builder()
                .phone(phone)
                .role(new Role(3L,"UNREGISTERED"))
                .build();
        return userDAO.save(user);
    }

    @Override
    public boolean setAdditional(AdditionalUserInformationDto dto,Long userId) {
        userDAO.setAdditional(dto.getUtilDate(),dto.getFullName(),userId);
        return true;

    }

    @Override
    public User setPassword(PasswordDto dto,Long userId) {
        userDAO.changePassword(passwordEncoder.encode(dto.getPassword()),userId);
        User user = userDAO.findById(userId).orElseThrow();
        System.out.println(user.toString());
        setRole(2L,user);
        return user;
    }

    @Override
    public boolean setRole(Long roleId, User user) {
        user.setRole(roleDAO.findById(roleId).orElseThrow());
        userDAO.save(user);
        return true;
    }


}
