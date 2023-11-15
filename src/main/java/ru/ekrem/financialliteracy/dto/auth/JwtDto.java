package ru.ekrem.financialliteracy.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtDto {
    private String accessToken;
    private String refreshToken;
}
