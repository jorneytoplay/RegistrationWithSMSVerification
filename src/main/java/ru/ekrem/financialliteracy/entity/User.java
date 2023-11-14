package ru.ekrem.financialliteracy.entity;


import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@ToString
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String phone;
    private String mail;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "date_of_birth")
    private java.util.Date dateOfBirth;
    @Column(name = "password")
    private String password;
    private String refreshToken;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;
}
