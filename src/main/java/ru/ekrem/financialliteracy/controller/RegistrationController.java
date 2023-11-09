package ru.ekrem.financialliteracy.controller;

import ru.ekrem.financialliteracy.Smsc;
import ru.ekrem.financialliteracy.dto.registration.NicknameDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ekrem.financialliteracy.service.RegistrationService;

import java.util.Arrays;

@RestController
@RequestMapping("/registration")
public class RegistrationController {

    Smsc sms = new Smsc();
    @Autowired
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @GetMapping("/info")
    public String getInformationAboutRegistrationSteps(){
        return "BLABLA";
    }

    @PostMapping("/nickname")
    public boolean indicateNickname(@RequestBody NicknameDTO nickname){
        return registrationService.indicateName(nickname);
    }

    @GetMapping
    public void sendPhone(){
        System.out.println("PHONE SENDER");
        String[] ret = sms.send_sms("79052729311", "Ваш пароль: 123", 1, "", "", 0, "", "");
        System.out.println(Arrays.toString(ret));
    }
}
