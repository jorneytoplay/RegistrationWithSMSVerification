package ru.ekrem.financialliteracy.dto;

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
