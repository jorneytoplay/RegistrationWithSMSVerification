package ru.ekrem.financialliteracy.dto.auth;

import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LoginDto {
    String phone;
    String password;
}
