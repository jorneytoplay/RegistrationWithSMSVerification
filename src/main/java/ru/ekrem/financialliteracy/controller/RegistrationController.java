package ru.ekrem.financialliteracy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.ekrem.financialliteracy.dto.LoginDto;
import ru.ekrem.financialliteracy.dto.registration.PhoneSmsDto;
import ru.ekrem.financialliteracy.security.AuthService;
import ru.ekrem.financialliteracy.security.JwtResponse;
import ru.ekrem.financialliteracy.security.JwtUser;
import ru.ekrem.financialliteracy.service.impl.SmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ekrem.financialliteracy.service.RegistrationService;

import javax.validation.constraints.NotNull;
import java.security.Principal;
import java.util.Arrays;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private AuthService authService;

    @PostMapping("/phone")
    public JwtResponse setPhone(@NotNull @RequestBody String phone){
        return registrationService.setPhone(phone);
    }

    @PreAuthorize("hasRole('UNREGISTERED')")
    @PostMapping("/confirmPhonePassword")
    public ResponseEntity<JwtResponse> confirmPhone(@RequestBody LoginDto dto){
        Authentication jwtUser = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(jwtUser.toString());
        boolean hasRoleClient = jwtUser.getAuthorities().stream()
                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("ROLE_CLIENT"));


        return null;
    }


    @GetMapping
    public void sendPhone(){
    }
}
