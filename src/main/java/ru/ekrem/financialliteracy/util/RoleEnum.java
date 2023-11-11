package ru.ekrem.financialliteracy.util;

public enum RoleEnum {
    ANONYMOUS(1);

    private int role;


    RoleEnum(int role) {
        this.role = role;
    }

    public int getRole() {
        return role;
    }
}
