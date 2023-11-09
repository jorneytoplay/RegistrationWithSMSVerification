package ru.ekrem.financialliteracy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ekrem.financialliteracy.entity.RegistrationUser;

@Repository
public interface RegistrationDAO extends JpaRepository<RegistrationUser,Long>{}
