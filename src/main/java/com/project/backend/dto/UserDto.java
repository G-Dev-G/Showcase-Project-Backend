package com.project.backend.dto;


public class UserDto {
    private Long userId;
    private String name;
    private String usernameEmail;
    private String address;
    private String userRole; // User/Admin

    public UserDto() {}

    public UserDto(Long userId, String name, String usernameEmail, String address, String userRole) {
        this.userId = userId;
        this.name = name;
        this.usernameEmail = usernameEmail;
        this.address = address;
        this.userRole = userRole;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsernameEmail() {
        return usernameEmail;
    }

    public void setUsernameEmail(String usernameEmail) {
        this.usernameEmail = usernameEmail;
    }

    public String getAddress() { return address; }

    public void setAddress(String address) { this.address = address; }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }
}
