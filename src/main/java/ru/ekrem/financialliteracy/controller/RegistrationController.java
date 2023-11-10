package ru.ekrem.financialliteracy.controller;

import ru.ekrem.financialliteracy.dto.registration.PhoneSmsDto;
import ru.ekrem.financialliteracy.service.impl.SmsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.ekrem.financialliteracy.service.RegistrationService;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/registration")
public class RegistrationController {
    @Autowired
    SmsServiceImpl sms;

    @Autowired
    private final RegistrationService registrationService;

    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }


    @PostMapping("/phone")
    public boolean setPhone(@NotNull @RequestBody String phone){
        return registrationService.setPhone(phone);
    }

    @PostMapping("/confirmPhone")
    public boolean confirmPhone(@RequestBody PhoneSmsDto dto){
        return registrationService.confirmPhone(dto);
    }


    /*@GetMapping
    public void sendPhone(){
        System.out.println("PHONE SENDER");
        //String[] ret = sms.send_sms("79052729311", "Ваш пароль: 123", 0);
       // System.out.println(sms.get_balance());
        //System.out.println(Arrays.toString(ret));
        System.out.println(Arrays.toString(sms.sendSms("79052729311", "Код: 2345", 0,1)));
       // System.out.println(Arrays.toString(sms.get_sms_cost("79121285212", "Код: 2345", 0)));
    }*/
}
