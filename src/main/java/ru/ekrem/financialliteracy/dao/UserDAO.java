package ru.ekrem.financialliteracy.dao;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ekrem.financialliteracy.entity.RegistrationUser;
import ru.ekrem.financialliteracy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDAO extends JpaRepository<User,Long> {
    User getByPhone(String phone);

    @Modifying
    @Query("UPDATE User e SET e.refreshToken = :refreshToken WHERE e.phone = :phone")
    User setRefreshToken(@Param("phone") String phone, @Param("refreshToken") String refreshToken);
}
