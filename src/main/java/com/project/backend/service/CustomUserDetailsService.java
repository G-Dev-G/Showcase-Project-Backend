package com.project.backend.service;

import com.project.backend.dto.UserDto;
import com.project.backend.email.EmailService;
import com.project.backend.model.AdminRegisterCode;
import com.project.backend.model.EmailConfirmation;
import com.project.backend.model.User;
import com.project.backend.repository.AdminRegisterCodeRepository;
import com.project.backend.repository.EmailConfirmationRepository;
import com.project.backend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final EmailConfirmationRepository emailConfirmationRepository;
    private final AdminRegisterCodeRepository adminRegisterCodeRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    @Autowired
    public CustomUserDetailsService(UserRepository userRepository, EmailConfirmationRepository emailConfirmationRepository, AdminRegisterCodeRepository adminRegisterCodeRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailConfirmationRepository = emailConfirmationRepository;
        this.adminRegisterCodeRepository = adminRegisterCodeRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
    }

    /**
     * pass in unique email to validate - For Spring Security override
     * @param email
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByUsernameEmail(email).orElseThrow(() ->
                new UsernameNotFoundException("User not found.")
        );
    }

    /**
     * pass in unique id to validate - For REST API calls
     * @param id
     * @return User
     */
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    /**
     * register user
     * @param user
     * @return User
     */
    public User addUser(User user) {
        // encrypt password
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public boolean userExists(String email) {
        return userRepository.findByUsernameEmail(email).isPresent();
    }

    /**
     * Update Name and Address
     * @param userId
     * @param name
     * @param address
     * @return User
     */
    public User updateNameAndAddress(Long userId, String name, String address) {
        // retrieve current user in db
        User userToUpdate = userRepository.findById(userId).orElse(null);
        // validation
        if (userToUpdate != null) {
            userToUpdate.setName(name);
            userToUpdate.setAddress(address);
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    /**
     * Update Password
     * @param userId
     * @param currentPassword
     * @param newPassword
     * @return User
     */
    public User updatePassword(Long userId, String currentPassword, String newPassword) {
        // retrieve current user in db
        User userToUpdate = userRepository.findById(userId).orElse(null);

        // validation
        if (userToUpdate != null) {
            // validate if current password is correct
            if (!bCryptPasswordEncoder.matches(currentPassword, userToUpdate.getPassword())) {
                return null;
            }
            // process and store new encoded password if old password matches
            userToUpdate.setPassword(bCryptPasswordEncoder.encode(newPassword));
            return userRepository.save(userToUpdate);
        }
        return null;
    }

    /**
     * Reset Password by Email
     * @param usernameEmail
     * @param newPassword
     * @return String
     */
    public String resetPasswordByUsernameEmail(String usernameEmail, String newPassword) {
        // retrieve current user in db
        User userToUpdate = userRepository.findByUsernameEmail(usernameEmail).orElse(null);

        // validation
        if (userToUpdate != null) {
            // store new encoded password
            userToUpdate.setPassword(bCryptPasswordEncoder.encode(newPassword));
            userRepository.save(userToUpdate);
            return "success";
        }
        return null;
    }

    /**
     * Send Email Verification Code for Register
     * @param usernameEmail
     */
    @Transactional
    public void sendCodeToRegister(String usernameEmail) {
        String code = saveVerificationCode(usernameEmail);
        // send email
        emailService.sendEmail(usernameEmail, "[AUTO MESSAGE] Confirm your email",
                "<h2 style='text-align: center; margin-bottom: 10px'>WELCOME!</h2>" +
                        "<p>The verification code to complete your registration is - </p>" +
                        "<h3 style='text-align: center; margin: 5px auto'>" + code + "</h3>" +
                        "<p style='text-align: center'>The code will be expired in 5 minutes.</p>" +
                        "<br />" +
                        "<p style='border-top: 1px solid gray; color: grey; font-size: small'>Please ignore this email if you did not register.</p>");
    }

    /**
     * Send Email Verification Code for Password Reset
     * @param usernameEmail
     */
    @Transactional
    public void sendCodeToResetPassword(String usernameEmail) {
        String code = saveVerificationCode(usernameEmail);
        // send email
        emailService.sendEmail(usernameEmail, "[AUTO MESSAGE] Reset your password",
                "<p>The verification code to reset your password is - </p>" +
                        "<h3 style='text-align: center; margin: 5px auto'>" + code + "</h3>" +
                        "<p style='text-align: center'>The code will be expired in 5 minutes.</p>" +
                        "<br />" +
                        "<p style='border-top: 1px solid gray; color: grey; font-size: small'>Please ignore this email if you did not perform this operation.</p>");
    }

    /**
     * Store Email Verification Code to DB
     * @param usernameEmail
     * @return String
     */
    public String saveVerificationCode(String usernameEmail) {
        // generate random 4-digit number code
        Random random = new Random();
        String code = String.format("%04d", random.nextInt(10000));

        // create new confirmation
        Calendar calendar = Calendar.getInstance(); // now
        calendar.add(Calendar.MINUTE, 5); // expire after 5 minutes
        Date expiredAt = calendar.getTime();

        EmailConfirmation emailConfirmation = new EmailConfirmation(
                null,
                usernameEmail,
                code,
                expiredAt
        );
        // =============== save confirmation ===============
        // refresh - delete potential confirmation in case the account had one before
        emailConfirmationRepository.deleteAllByUsernameEmail(emailConfirmation.getUsernameEmail());
        // save
        emailConfirmationRepository.save(emailConfirmation);
        return code;
    }

    /**
     * Confirm the email verification code
     * @param usernameEmail
     * @param verificationCode
     * @return String
     */
    public String confirmVerificationCode(String usernameEmail, String verificationCode) {
        // check if verification code is correct
        EmailConfirmation confirmation = emailConfirmationRepository.findTopByUsernameEmailAndVerificationCode(usernameEmail, verificationCode).orElse(null);
        // Verification code not found
        if (confirmation == null) {
            return "code incorrect";
        }
        // Verification code expired
        if (confirmation.getExpiredAt().before(new Date())) {
            return "code expired";
        }
        return "success";
    }

    /**
     * Confirm the admin register code
     * @param adminRegisterCode
     * @return String
     */
    public String confirmAdminRegisterCode(String adminRegisterCode) {
        // try to find the code in DB
        AdminRegisterCode registerCode = adminRegisterCodeRepository.findByRegisterCode(adminRegisterCode).orElse(null);
        // not found
        if (registerCode == null)
            return "admin code incorrect";
        return "success";
    }

    /**
     * Entity to Dto conversion
     * @param user
     * @return userDto
     */
    public UserDto userEntityToDto(User user) {
        if (user != null) {
            UserDto userDto = new UserDto(
                    user.getUserId(),
                    user.getName(),
                    user.getUsernameEmail(),
                    user.getAddress(),
                    user.getUserRole().name()
            );
            return userDto;
        }
        return null;
    }
}
