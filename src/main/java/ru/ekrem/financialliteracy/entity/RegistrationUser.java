package ru.ekrem.financialliteracy.entity;


import lombok.*;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "registration_users")
public class RegistrationUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String fullName;

    private Long registration_step;
    private boolean registration_complete;
}
