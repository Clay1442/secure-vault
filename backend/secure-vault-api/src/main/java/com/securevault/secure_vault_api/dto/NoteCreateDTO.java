package com.securevault.secure_vault_api.dto;

import java.time.LocalDateTime;

public class NoteCreateDTO {

    private String title;

    private String username;

    private String password;

    private String description;

    private LocalDateTime createdAt;

    public NoteCreateDTO(String title) {
        super();
    }

    public NoteCreateDTO(String title, String username, String password, String description) {
        this.title = title;
        this.username = username;
        this.password = password;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
