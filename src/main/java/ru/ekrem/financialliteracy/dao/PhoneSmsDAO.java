package ru.ekrem.financialliteracy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.ekrem.financialliteracy.entity.PhoneSms;

@Repository
public interface PhoneSmsDAO extends JpaRepository<PhoneSms,Long> {

    @Modifying
    @Query("DELETE FROM PhoneSms e WHERE e.userId=:userId AND  e.code=:code")
    int deleteByUserIdAndCode(@Param("userId") Long useId, @Param("code")Long code);
}
