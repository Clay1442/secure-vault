package com.securevault.secure_vault_api.security;


import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component("userSecurity")
public class UserSecurity {
    private final UserRepository userRepository;

    public UserSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Authentication authentication, Long userId) {
        if (authentication == null || userId == null) {
            return false;
        }

        String userEmail = authentication.getName();

        User authenticatedUser = userRepository.findByEmail(userEmail).orElse(null);

        if (authenticatedUser == null) {
            return false;
        }

        return authenticatedUser.getId().equals(userId);
    }

}
