package ru.ekrem.financialliteracy.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
public class PhoneSms {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long code;
    private Long createDate;

}
