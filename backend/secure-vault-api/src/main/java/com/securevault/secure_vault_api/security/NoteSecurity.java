package com.securevault.secure_vault_api.security;

import com.securevault.secure_vault_api.entities.Note;
import com.securevault.secure_vault_api.repositories.NoteRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@Component("noteSecurity")
public class NoteSecurity {

    private final NoteRepository noteRepository;

    public NoteSecurity(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @Transactional(readOnly = true)
    public boolean isOwner(Authentication authentication, Long noteId){
        if(authentication == null || noteId == null) {
            return false;
        }

        String userEmail = authentication.getName();
        Note note = noteRepository.findById(noteId).orElse(null);

        return note != null && note.getUser().getEmail().equals(userEmail);
    }
}
