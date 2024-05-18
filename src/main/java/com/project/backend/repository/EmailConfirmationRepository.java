package com.project.backend.repository;

import com.project.backend.model.EmailConfirmation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailConfirmationRepository extends JpaRepository<EmailConfirmation, Long> {
    // Delete
    void deleteAllByUsernameEmail(String usernameEmail);

    // Find EmailConfirmation
    Optional<EmailConfirmation> findTopByUsernameEmailAndVerificationCode(String usernameEmail, String verificationCode);
}
