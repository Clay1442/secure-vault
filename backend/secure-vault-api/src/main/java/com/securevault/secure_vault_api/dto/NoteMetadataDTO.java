package com.securevault.secure_vault_api.dto;

import com.securevault.secure_vault_api.entities.Note;

import java.time.LocalDateTime;

public class NoteMetadataDTO {

    private Long id;

    private String title;

    private LocalDateTime createdAt;

    private LocalDateTime updateAt;

    public NoteMetadataDTO() {
    }

    public NoteMetadataDTO(Note note) {
        this.id = note.getId();
        this.title = note.getTitle();
        this.createdAt = note.getCreatedAt();
        this.updateAt = note.getUpdateAt();
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
}
