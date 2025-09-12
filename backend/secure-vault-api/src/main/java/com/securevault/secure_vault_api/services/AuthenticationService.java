package com.securevault.secure_vault_api.services;

import ch.qos.logback.classic.Logger;
import com.securevault.secure_vault_api.entities.PasswordResetToken;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.exceptions.ResourceNotFoundException;
import com.securevault.secure_vault_api.repositories.PasswordResetTokenRepository;
import com.securevault.secure_vault_api.repositories.UserRepository;
import org.slf4j.LoggerFactory;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;


@Service
public class AuthenticationService implements UserDetailsService {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private static final Logger logger = (Logger) LoggerFactory.getLogger(AuthenticationService.class);
    private final EmailService emailService;


    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder, PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public User getAuthenticatedUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("Authenticated user not found"));
    }


    //Password Reset
    public void  createPasswordResetTokenForUser(String userEmail){
        Optional<User> userOptional = userRepository.findByEmail(userEmail);

        if (userOptional.isEmpty()) {
            logger.warn("Password reset attempt for non-existent email: {}", userEmail);
            return;
        }

        User user = userOptional.get();

        String token = UUID.randomUUID().toString();
        PasswordResetToken resetToke = new PasswordResetToken(token, user);
        passwordResetTokenRepository.save(resetToke);


        emailService.sendPasswordResetEmail(user, token);

        logger.info("Password reset token created for user: {}", userEmail);
    }

    public void resetPassword(String token, String newPassword){
        PasswordResetToken validToken =  passwordResetTokenRepository.findByToken(token)
                .orElseThrow(() -> new ResourceNotFoundException("Token invalid or expired"));
        if(validToken.getExpiryDate().isBefore(LocalDateTime.now())){
            passwordResetTokenRepository.delete(validToken);
            throw new ResourceNotFoundException("Token expired");
        }

        User user = validToken.getUser();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        passwordResetTokenRepository.delete(validToken);
        logger.info("Password has been successfully reset for user: {}", user.getEmail());
    }

}
