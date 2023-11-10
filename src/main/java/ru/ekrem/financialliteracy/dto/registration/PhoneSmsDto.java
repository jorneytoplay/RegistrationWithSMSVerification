package ru.ekrem.financialliteracy.dto.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class PhoneSmsDto {
    String phone;
    Long code;
}
