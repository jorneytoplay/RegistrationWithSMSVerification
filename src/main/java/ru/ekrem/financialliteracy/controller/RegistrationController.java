package ru.ekrem.financialliteracy.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import ru.ekrem.financialliteracy.dto.ResponseData;
import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.ConfirmCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.dto.auth.JwtDto;
import ru.ekrem.financialliteracy.service.RegistrationService;
import ru.ekrem.financialliteracy.util.RegistrationValidator;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@RestController
@RequestMapping("/registration")
@Tag(name = "Регистрация")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;
    @Autowired
    private RegistrationValidator verification;

    @Operation(summary = "Ввод номера телефона")
    @PostMapping("/phone")
    public ResponseData<JwtDto> setPhone(
            @NotBlank
            @Pattern(regexp = "^7\\d{10}$",
                    message = "Не соответствует формату номера телефона")
            @RequestBody String phone){
        return ResponseData.<JwtDto>builder()
                .success(true)
                .data(registrationService.setPhone(phone))
                .build();
    }

    @Operation(summary = "Подтверждение номера телефона")
    @PreAuthorize("hasRole('UNREGISTERED')")
    @PostMapping("/confirmPhonePassword")
    public ResponseData<JwtDto> confirmPhone(@RequestBody ConfirmCodeDto dto, Authentication authentication){
        verification.getVerification(false,authentication);
        return ResponseData.<JwtDto>builder()
                .success(true)
                .data(registrationService.confirmPhonePassword(dto, Long.valueOf(authentication.getName())))
                .build();
    }

    @Operation(summary = "Установка дополнительной информации")
    @PreAuthorize("hasRole('UNREGISTERED')")
    @PostMapping("/setAdditional")
    public ResponseData setAdditionalInformation(@RequestBody AdditionalUserInformationDto dto, Authentication authentication){
        verification.getVerification(true,authentication);
        return ResponseData.builder()
                .success(registrationService.setAdditional(dto, Long.valueOf(authentication.getName())))
                .build();
    }

    @Operation(summary = "Установка пароля")
    @PreAuthorize("hasRole('UNREGISTERED')")
    @PostMapping("/setPassword")
    public ResponseData<JwtDto> setPassword(@RequestBody PasswordDto dto, Authentication authentication){
        verification.getVerification(true,authentication);
        return ResponseData.<JwtDto>builder()
                .success(true)
                .data(registrationService.setPassword(dto, Long.valueOf(authentication.getName())))
                .build();
    }

}
