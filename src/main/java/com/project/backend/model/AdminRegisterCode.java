package com.project.backend.model;

import javax.persistence.*;

@Entity
@Table(name = "AdminRegisterCode")
public class AdminRegisterCode {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(updatable = false)
    private Long adminRegisterCodeId; // primary key
    private String registerCode; // the code for admin to register

    public AdminRegisterCode() {}

    public AdminRegisterCode(Long adminRegisterCodeId, String registerCode) {
        this.adminRegisterCodeId = adminRegisterCodeId;
        this.registerCode = registerCode;
    }

    public Long getAdminRegisterCodeId() {
        return adminRegisterCodeId;
    }

    public String getRegisterCode() {
        return registerCode;
    }

    public void setRegisterCode(String registerCode) {
        this.registerCode = registerCode;
    }
}
