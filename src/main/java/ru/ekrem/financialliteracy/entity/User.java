package ru.ekrem.financialliteracy.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String phone;
    private String mail;
    private String fullName;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    private String password;
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
