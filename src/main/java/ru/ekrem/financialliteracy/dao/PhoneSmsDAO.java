package ru.ekrem.financialliteracy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ekrem.financialliteracy.entity.PhoneSms;

@Repository
public interface PhoneSmsDAO extends JpaRepository<PhoneSms,Long> {

    PhoneSms findByUserIdAndCode(Long useId,Long code);
}
