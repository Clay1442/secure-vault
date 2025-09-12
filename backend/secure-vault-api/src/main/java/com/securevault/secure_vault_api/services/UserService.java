package com.securevault.secure_vault_api.services;

import com.securevault.secure_vault_api.dto.UserCreateDTO;
import com.securevault.secure_vault_api.dto.UserDTO;
import com.securevault.secure_vault_api.dto.UserUpdateDTO;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.entities.enums.Role;
import com.securevault.secure_vault_api.exceptions.ExistingObject;
import com.securevault.secure_vault_api.exceptions.ResourceNotFoundException;
import com.securevault.secure_vault_api.repositories.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

   private final UserRepository userRepository;
   private final PasswordEncoder passwordEncoder;

   public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
       this.userRepository = userRepository;
       this.passwordEncoder = passwordEncoder;
   }

    protected User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserDTO> findAll() {
            List<User> users = userRepository.findAll();
            return users.stream().map(UserDTO::new).collect(Collectors.toList());
    }

    //Returns if user is Admin or if user is equal to authenticated user
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isOwner(authentication, #id)")
    public UserDTO findById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        return new UserDTO(user);
    }

    public UserDTO create(UserCreateDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        if(userRepository.findByEmail(dto.getEmail()).isPresent()){
            throw new ExistingObject("User with this email already exists");
        }
        user.setEmail(dto.getEmail());

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.roleAdd(Role.ROLE_CLIENT);

        return new UserDTO(userRepository.save(user));
    }

    @Transactional
    public UserDTO update(UserUpdateDTO dto) {
        User user = getAuthenticatedUser();
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            if (dto.getOldPassword() == null || !passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Old password does not match current password");
            }
            user.setPassword(passwordEncoder.encode(dto.getPassword()));
        }

        if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
            userRepository.findByEmail(dto.getEmail()).filter(existingUser -> !existingUser.getId().equals(user.getId()))
                    .ifPresent(existingUser -> {
                        throw new ExistingObject("User with this email already exists");
                    });
            user.setEmail(dto.getEmail());
        }

        if (dto.getName() != null && !dto.getName().isBlank()) {
            user.setName(dto.getName());
        }

        User userUpdate = userRepository.save(user);
        return new UserDTO(userUpdate);
    }


    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isOwner(authentication, #id)")
    public void delete(Long id) {
            User userToDelete = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            userRepository.delete(userToDelete);
            System.out.println("User with ID " + id + " deleted successfully.");

    }

}
