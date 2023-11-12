package ru.ekrem.financialliteracy.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.ekrem.financialliteracy.entity.Role;
@Repository
public interface RoleDAO extends JpaRepository<Role,Long> {
}
