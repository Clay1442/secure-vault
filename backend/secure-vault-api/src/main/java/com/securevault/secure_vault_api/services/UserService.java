package com.securevault.secure_vault_api.services;

import com.securevault.secure_vault_api.dto.UserCreateDTO;
import com.securevault.secure_vault_api.dto.UserDTO;
import com.securevault.secure_vault_api.dto.UserUpdateDTO;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.entities.enums.Role;
import com.securevault.secure_vault_api.exceptions.ExistingObject;
import com.securevault.secure_vault_api.exceptions.ResourceNotFoundException;
import com.securevault.secure_vault_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        return userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

    }

    public List<UserDTO> findAll() {
        User user = getAuthenticatedUser();
        if(user.getRoles().contains(Role.ROLE_ADMIN)){
            List<User> users = userRepository.findAll();
            List<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toList());
            return userDTOs;
        }else {
            throw new AccessDeniedException("You are not allowed to access this resource.");
        }
    }

    //Returns if user is Admin or if user is equal to authenticated user
    public UserDTO findById(Long id) {
        User authUser = getAuthenticatedUser();
        if (authUser.getRoles().contains(Role.ROLE_ADMIN) || authUser.getId().equals(id)) {
            User user = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            return new UserDTO(user);
        } else {
            throw new AccessDeniedException("You do not have permission to access this user's data.");
        }
    }


    public UserDTO create(UserCreateDTO dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return new UserDTO(userRepository.save(user));
    }

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


    public void delete(Long id) {
        User authUser = getAuthenticatedUser();
        if (authUser.getRoles().contains(Role.ROLE_ADMIN) || authUser.getId().equals(id)) {
            User userToDelete = userRepository.findById(id)
                    .orElseThrow(() -> new ResourceNotFoundException("User not found"));
            userRepository.delete(userToDelete);

            System.out.println("User with ID " + id + " deleted successfully.");
        }else {
            throw new AccessDeniedException("You do not have permission to access this user's data.");
        }
    }

}
