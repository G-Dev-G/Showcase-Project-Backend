package com.project.backend.controller;

import com.project.backend.dto.UserDto;
import com.project.backend.model.User;
import com.project.backend.model.UserRole;
import com.project.backend.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping(path="/auth")
public class AuthController {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthController(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/register")
    public Map<String, Object> register(
            @RequestParam("name") String name,
            @RequestParam("usernameEmail") String usernameEmail,
            @RequestParam("password") String password,
            @RequestParam("userRole") String userRole,
            @RequestParam("verificationCode") String verificationCode,
            @RequestParam("adminRegisterCode") String adminRegisterCode) {
        HashMap<String, Object> mapToReturn = new HashMap<>();

        // check if user exists
        if (customUserDetailsService.userExists(usernameEmail)) {
            mapToReturn.put("msg", "user exists");
            return mapToReturn;
        }

        // check verification code
        String confirmationMsg = customUserDetailsService.confirmVerificationCode(usernameEmail, verificationCode);
        if (!confirmationMsg.equals("success")) {
            mapToReturn.put("msg", confirmationMsg);
            return mapToReturn;
        }

        // check admin register code
        if (userRole.toUpperCase().equals(UserRole.ADMIN.name())) {
            String adminCodeMsg = customUserDetailsService.confirmAdminRegisterCode(adminRegisterCode);
            if (!adminCodeMsg.equals("success")) {
                mapToReturn.put("msg", adminCodeMsg);
                return mapToReturn;
            }
        }

        // Success
        try {
            UserRole userRoleObj = UserRole.valueOf(userRole.toUpperCase()); // catch exception
            User user = new User(null, name, usernameEmail, null, password, userRoleObj);
            // save
            User userSaved = customUserDetailsService.addUser(user);
            mapToReturn.put("msg", "success");
            mapToReturn.put("userDto", customUserDetailsService.userEntityToDto(userSaved));
            return mapToReturn;
        }
        catch (IllegalArgumentException e) {
            mapToReturn.put("msg", "fail");
            return mapToReturn;
        }
    }

    /**
     * Secure login API, validate through HTTP header
     * If call is successful, it means user entered authenticated info
     * Use this newly created session, return sessionID
     *
     * @return
     */
    @GetMapping("/login")
    public Map<String, Object> login(@AuthenticationPrincipal User user, HttpSession session) {
        HashMap<String, Object> mapToReturn = new HashMap<>();
        UserDto userDto = customUserDetailsService.userEntityToDto(user);
        mapToReturn.put("token", session.getId()); // return sessionID as token
        mapToReturn.put("userDto", userDto); // return userDto
        return mapToReturn;
    }

    @GetMapping("/logout")
    public Map<String, String> logout(HttpServletRequest request) {
        // destroy session
        HttpSession session = request.getSession(false);
        if (session != null)
            session.invalidate();

        // logout with current authentication
        SecurityContextHolder.clearContext();

        // check if authentication is successfully destroyed
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null)
            return Collections.singletonMap("msg", "failed");
        return Collections.singletonMap("msg", "success");
    }

    @GetMapping("/getUserById/{id}")
    public UserDto getUserById(@PathVariable(value="id") Long id) {
        User gotUser = customUserDetailsService.getUserById(id);
        return customUserDetailsService.userEntityToDto(gotUser);
    }

    @PutMapping("/updateNameAndAddress")
    public UserDto updateNameAndAddress(
            @RequestParam("userId") Long userId,
            @RequestParam("name") String name,
            @RequestParam("address") String address) {
        User userUpdated = customUserDetailsService.updateNameAndAddress(userId, name, address);
        return customUserDetailsService.userEntityToDto(userUpdated);
    }

    @PutMapping("/updatePassword")
    public Map<String, Object> updatePassword(
            @RequestParam("userId") Long userId,
            @RequestParam("currentPassword") String currentPassword,
            @RequestParam("newPassword") String newPassword) {
        HashMap<String, Object> mapToReturn = new HashMap<>();
        // Update
        User userUpdated = customUserDetailsService.updatePassword(userId, currentPassword, newPassword);

        if (userUpdated != null) {
            mapToReturn.put("passwordCorrect", true);
            mapToReturn.put("userDto", customUserDetailsService.userEntityToDto(userUpdated));
        }
        // return null if current password is not matched or user not found
        else {
            mapToReturn.put("passwordCorrect", false);
        }
        return mapToReturn;
    }

    @PutMapping("/sendEmailCodeToRegister")
    public Map<String, String> sendEmailCodeToRegister(@RequestParam("email") String usernameEmail) {
        customUserDetailsService.sendCodeToRegister(usernameEmail);
        return Collections.singletonMap("msg", "success");
    }

    @PutMapping("/sendEmailCodeToResetPassword")
    public Map<String, String> sendEmailCodeToResetPassword(@RequestParam("email") String usernameEmail) {
        // check if user exists
        if (!customUserDetailsService.userExists(usernameEmail)) {
            return Collections.singletonMap("msg", "user not existed");
        }
        // send code to existed user in order to reset password
        customUserDetailsService.sendCodeToResetPassword(usernameEmail);
        return Collections.singletonMap("msg", "success");
    }

    @PutMapping("/resetPasswordByEmailCode")
    public Map<String, String> resetPasswordByEmailCode(
            @RequestParam("email") String usernameEmail,
            @RequestParam("verificationCode") String verificationCode,
            @RequestParam("newPassword") String newPassword) {
        // check if user exists
        if (!customUserDetailsService.userExists(usernameEmail)) {
            return Collections.singletonMap("msg", "user not existed");
        }
        // check verification code
        String confirmationMsg = customUserDetailsService.confirmVerificationCode(usernameEmail, verificationCode);
        if (!confirmationMsg.equals("success"))
            return Collections.singletonMap("msg", confirmationMsg);
        // reset password
        String resetMsg = customUserDetailsService.resetPasswordByUsernameEmail(usernameEmail, newPassword);
        return Collections.singletonMap("msg", resetMsg);
    }
}
