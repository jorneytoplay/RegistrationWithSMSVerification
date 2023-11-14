package ru.ekrem.financialliteracy.util;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import ru.ekrem.financialliteracy.handler.exception.RegistrationProcessException;

@Component
public class RegistrationValidator {

    public boolean getVerification(boolean needed, Authentication authentication) {
        if (!(Boolean) authentication.getDetails() == needed){
            if(needed){
                throw new RegistrationProcessException("Подтвердите номер телефона");
            }else{
                throw new RegistrationProcessException("Вы уже подтвердили номер телефона");
            }
        }
        return true;
    }

    public boolean validateStep(Long expected, Long available){
        if(!expected.equals(available)){
            int intExcepted = Math.toIntExact(expected);
            switch (intExcepted){
                case 0:
                    throw new RegistrationProcessException("Укажите номер телефона");
                case 1:
                    throw new RegistrationProcessException("Подтвердите номер телефона");
                case 2:
                    throw new RegistrationProcessException("Укажите дополнительную инофрмацию");
                case 3:
                    throw new RegistrationProcessException("Укажите пароль");

            }
        }
        return true;
    }


}
