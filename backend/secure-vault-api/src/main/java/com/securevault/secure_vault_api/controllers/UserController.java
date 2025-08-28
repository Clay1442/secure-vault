package com.securevault.secure_vault_api.controllers;

import com.securevault.secure_vault_api.dto.UserCreateDTO;
import com.securevault.secure_vault_api.dto.UserDTO;
import com.securevault.secure_vault_api.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping(value = "/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable long id ) {
        UserDTO userFound = userService.findById(id);
        return ResponseEntity.ok(userFound);
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll(){
        List<UserDTO> users = userService.findAll();
        return ResponseEntity.ok().body(users);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@Valid @RequestBody UserCreateDTO dto) {
        UserDTO user =  userService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(user.getId()).toUri();
        return ResponseEntity.created(uri).body(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        userService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
