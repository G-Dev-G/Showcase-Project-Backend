package com.project.backend.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "EmailConfirmation")
public class EmailConfirmation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long emailConfirmationId; // primary key

    private String usernameEmail; // associated email to confirm
    private String verificationCode; // 4-digit number code

    @Temporal(TemporalType.TIMESTAMP)
    private Date expiredAt;

    public EmailConfirmation() {
    }

    public EmailConfirmation(Long emailConfirmationId, String usernameEmail, String verificationCode, Date expiredAt) {
        this.emailConfirmationId = emailConfirmationId;
        this.usernameEmail = usernameEmail;
        this.verificationCode = verificationCode;
        this.expiredAt = expiredAt;
    }

    public Long getEmailConfirmationId() {
        return emailConfirmationId;
    }

    public String getUsernameEmail() {
        return usernameEmail;
    }

    public void setUsernameEmail(String usernameEmail) {
        this.usernameEmail = usernameEmail;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public Date getExpiredAt() {
        return expiredAt;
    }

    public void setExpiredAt(Date expiredAt) {
        this.expiredAt = expiredAt;
    }
}
