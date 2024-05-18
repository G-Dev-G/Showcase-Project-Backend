package com.project.backend.repository;

import com.project.backend.model.AdminRegisterCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRegisterCodeRepository extends JpaRepository<AdminRegisterCode, Long> {
    Optional<AdminRegisterCode> findByRegisterCode(String registerCode);
}
