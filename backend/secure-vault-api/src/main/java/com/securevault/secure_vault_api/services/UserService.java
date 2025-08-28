package com.securevault.secure_vault_api.services;

import com.securevault.secure_vault_api.dto.UserCreateDTO;
import com.securevault.secure_vault_api.dto.UserDTO;
import com.securevault.secure_vault_api.dto.UserUpdateDTO;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.exceptions.ExistingObject;
import com.securevault.secure_vault_api.exceptions.ResourceNotFoundException;
import com.securevault.secure_vault_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;


    protected User findByEntity(long id){
        //implementar exception depois
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public UserDTO findById(long id){
        User user = findByEntity(id);
        return new UserDTO(user);
    }

    public Set<UserDTO> findAll(){
        List<User> users = userRepository.findAll();
        Set<UserDTO> userDTOs = users.stream().map(UserDTO::new).collect(Collectors.toSet());
        return userDTOs;
    }

    public UserDTO create(UserCreateDTO dto){
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        return new UserDTO( userRepository.save(user));
    }

     public UserDTO update(UserUpdateDTO dto, long id){
           User user = findByEntity(id);
           if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
               if(dto.getOldPassword() == null || ! passwordEncoder.matches(dto.getOldPassword(), user.getPassword())) {
                   throw new IllegalArgumentException("Old password does not match current password");
               }
                   user.setPassword(passwordEncoder.encode(dto.getPassword()));
           }

         if (dto.getEmail() != null && !dto.getEmail().isBlank()) {
             userRepository.findByEmail(dto.getEmail()).filter(existingUser -> existingUser.getId() != id)
                           .ifPresent(existingUser -> {
                               throw new ExistingObject("User with this email already exists");
                           });
           user.setEmail(dto.getEmail());
         }

         if(dto.getName() != null && ! dto.getName().isBlank()){
             user.setName(dto.getName());
         }

           userRepository.save(user);
           return new UserDTO(user);
     }


    public void delete(Long id){
      User user = findByEntity(id);
      userRepository.deleteById(user.getId());
    }

}
