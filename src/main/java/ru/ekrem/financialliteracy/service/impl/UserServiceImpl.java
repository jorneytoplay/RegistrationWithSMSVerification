package ru.ekrem.financialliteracy.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.ekrem.financialliteracy.dao.RoleDAO;
import ru.ekrem.financialliteracy.dao.UserDAO;
import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.entity.Role;
import ru.ekrem.financialliteracy.entity.User;
import ru.ekrem.financialliteracy.handler.exception.NotFoundException;
import ru.ekrem.financialliteracy.service.UserService;

import javax.transaction.Transactional;

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
    @Transactional
    public User setRefreshToken(String phone, String refreshToken) {
        System.out.println(phone);
        userDAO.setRefreshToken(phone,refreshToken);
        return userDAO.getByPhone(phone);
    }

    @Override
    public User createAnonymous(String phone) {
        User user = User.builder()
                .phone(phone)
                .role(new Role(4L,"UNREGISTERED"))
                .build();
        return userDAO.save(user);
    }

    @Override
    public boolean setAdditional(AdditionalUserInformationDto dto,Long userId) {
        getById(userId);
        userDAO.setAdditional(dto.getUtilDate(),dto.getFullName(),userId);
        return true;

    }

    @Override
    public User setPassword(PasswordDto dto,Long userId) {
        getById(userId);
        User user = userDAO.findById(userId).orElseThrow();
        user.setPassword(dto.getPassword());
        userDAO.save(user);
        setRole(2L,user);
        return user;
    }

    @Override
    public boolean setRole(Long roleId, User user) {
        user.setRole(roleDAO.findById(roleId).orElseThrow());
        userDAO.save(user);
        return true;
    }

    @Override
    public User getById(Long userId){
        User user = userDAO.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        return user;
    }


}
