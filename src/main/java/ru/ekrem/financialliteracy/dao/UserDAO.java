package ru.ekrem.financialliteracy.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;
import ru.ekrem.financialliteracy.entity.RegistrationUser;
import ru.ekrem.financialliteracy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User,Long> {
    User getByPhone(String phone);

    @Modifying
    @Query("UPDATE User e SET e.refreshToken = :refreshToken WHERE e.phone = :phone")
    Integer setRefreshToken(@Param("phone") String phone, @Param("refreshToken") String refreshToken);

    @Modifying
    @Query("UPDATE User e SET e.dateOfBirth = :dateOfBirth, e.fullName = :fullName WHERE e.id = :userId")
    Integer setAdditional(@Param("dateOfBirth") java.util.Date utilDate, @Param("fullName") String fullName, @Param("userId") Long userId);

    @Modifying
    @Query("UPDATE User e SET e.password = :password WHERE e.id = :userId")
    Integer changePassword(@Param("password") String password, @Param("userId") Long userId);
}
