package ru.ekrem.financialliteracy.security;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String refreshToken;
}
