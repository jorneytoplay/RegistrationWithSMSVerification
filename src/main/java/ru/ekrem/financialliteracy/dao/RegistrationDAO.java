package ru.ekrem.financialliteracy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ekrem.financialliteracy.entity.RegistrationUser;

import javax.transaction.Transactional;

@Repository
public interface RegistrationDAO extends JpaRepository<RegistrationUser,Long>{

    @Modifying
    @Query("UPDATE RegistrationUser e SET e.registration_step = :step WHERE e.userId = :userId")
    int updateStepByUserId(@Param("userId") Long userId, @Param("step") Long step);
}
