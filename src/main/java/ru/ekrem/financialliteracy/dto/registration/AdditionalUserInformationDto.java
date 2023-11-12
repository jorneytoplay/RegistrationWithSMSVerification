package ru.ekrem.financialliteracy.dto.registration;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AdditionalUserInformationDto {
    @Temporal(TemporalType.DATE)
    java.util.Date utilDate;
    String fullName;
}
