package ru.ekrem.financialliteracy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ekrem.financialliteracy.dto.auth.LoginDto;
import ru.ekrem.financialliteracy.dto.ResponseData;
import ru.ekrem.financialliteracy.dto.auth.RefreshTokenDto;
import ru.ekrem.financialliteracy.security.AuthService;
import ru.ekrem.financialliteracy.dto.auth.JwtDto;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @GetMapping("/login")
    public ResponseData<JwtDto> login(@RequestBody LoginDto dto){
        return ResponseData.<JwtDto>builder()
                .success(true)
                .data(authService.login(dto))
                .build();
    }

    @GetMapping("/refresh")
    public ResponseData<JwtDto> refresh(@RequestBody RefreshTokenDto dto){
        return ResponseData.<JwtDto>builder()
                .success(true)
                .data(authService.refresh(dto.getRefresh()))
                .build();
    }
}
