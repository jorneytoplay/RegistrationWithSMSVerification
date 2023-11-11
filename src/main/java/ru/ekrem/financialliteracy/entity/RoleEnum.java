package ru.ekrem.financialliteracy.entity;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@RequiredArgsConstructor
public enum RoleEnum implements GrantedAuthority {
    ADMIN,
    USER,
    UNREGISTERED;

    @Override
    public String getAuthority() {
        return null;
    }
}