package com.securevault.secure_vault_api.dto;

import com.securevault.secure_vault_api.entities.Note;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.entities.enums.Role;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserDTO {

    private Long id;

    private String name;

    private String email;

    private Set<NoteDTO> notes = new HashSet<>();

    public UserDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.notes = user.getNotes().stream().map(NoteDTO::new).collect(Collectors.toSet());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setFirstName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public Set<NoteDTO> getNotes() {
        return notes;
    }

}
