package ru.ekrem.financialliteracy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.ekrem.financialliteracy.dto.ResponseData;
import ru.ekrem.financialliteracy.dto.registration.AdditionalUserInformationDto;
import ru.ekrem.financialliteracy.dto.registration.ConfirmCodeDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ekrem.financialliteracy.dto.registration.PasswordDto;
import ru.ekrem.financialliteracy.security.JwtResponse;
import ru.ekrem.financialliteracy.service.RegistrationService;

import javax.validation.constraints.NotNull;
import java.security.Principal;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;


    @PreAuthorize(("hasRole('ANONYMOUS')"))
    @PostMapping("/phone")
    public ResponseData<JwtResponse> setPhone(@NotNull @RequestBody String phone){
        return ResponseData.<JwtResponse>builder()
                .success(true)
                .data(registrationService.setPhone(phone))
                .build();
    }

    @PreAuthorize("hasRole('UNREGISTERED')")
    @PostMapping("/confirmPhonePassword")
    public ResponseData confirmPhone(@RequestBody ConfirmCodeDto dto,Principal userPrincipal){
        return ResponseData.builder()
                .success(registrationService.confirmPhonePassword(dto, Long.valueOf(userPrincipal.getName())))
                .build();
    }

    @PreAuthorize("hasRole('UNREGISTERED')")
    @PostMapping("/setAdditional")
    @GetMapping
    public ResponseData setAdditionalInformation(@RequestBody AdditionalUserInformationDto dto, Principal userPrincipal){
        return ResponseData.builder()
                .success(registrationService.setAdditional(dto, Long.valueOf(userPrincipal.getName())))
                .build();
    }

    @PreAuthorize("hasRole('UNREGISTERED')")
    @PostMapping("/setPassword")
    @GetMapping
    public ResponseData<JwtResponse> setPassword(@RequestBody PasswordDto dto, Principal userPrincipal){
        return ResponseData.<JwtResponse>builder()
                .success(true)
                .data(registrationService.setPassword(dto, Long.valueOf(userPrincipal.getName())))
                .build();
    }
}
