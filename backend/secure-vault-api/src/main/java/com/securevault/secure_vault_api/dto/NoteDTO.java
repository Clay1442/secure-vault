package com.securevault.secure_vault_api.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.securevault.secure_vault_api.entities.Note;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;

import java.time.LocalDateTime;

public class NoteDTO{

    private Long id;

    private String title;

    private String username;

    private String password;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime createdAt;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime updateAt;

    private String description;

    public NoteDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.username = note.getUsername();
        this.password = note.getPassword();
        this.createdAt = note.getCreatedAt();
        this.updateAt = note.getUpdateAt();
        this.description = note.getDescription();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(LocalDateTime updateAt) {
        this.updateAt = updateAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
