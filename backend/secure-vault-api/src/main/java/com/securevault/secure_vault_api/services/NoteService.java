package com.securevault.secure_vault_api.services;


import com.securevault.secure_vault_api.dto.NoteCreateDTO;
import com.securevault.secure_vault_api.dto.NoteDTO;
import com.securevault.secure_vault_api.dto.NoteUpdateDTO;
import com.securevault.secure_vault_api.entities.Note;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.exceptions.ResourceNotFoundException;
import com.securevault.secure_vault_api.repositories.NoteRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class    NoteService {

    private final  NoteRepository noteRepository;
    private final AuthenticationService authenticationService;

    public  NoteService(NoteRepository noteRepository, AuthenticationService authenticationService) {
        this.noteRepository = noteRepository;
        this.authenticationService = authenticationService;
    }

    @Transactional(readOnly = true)
    @PreAuthorize("@noteSecurity.isOwner(authentication, #id)")
    public NoteDTO findById(Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
        return new NoteDTO(note);
    }

    @Transactional(readOnly = true)
    public List<NoteDTO> findAll() {
        User user = authenticationService.getAuthenticatedUser();
        List<Note> notes = noteRepository.findAllByUserId(user.getId());
        return notes.stream().map(NoteDTO::new).collect(Collectors.toList());
    }


    public NoteDTO create(NoteCreateDTO dto) {
        User user = authenticationService.getAuthenticatedUser();

        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setDescription(dto.getDescription());
        note.setUsername(dto.getUsername());
        note.setPassword(dto.getPassword());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);
        return new NoteDTO(savedNote);
    }

    @PreAuthorize("@noteSecurity.isOwner(authentication, #id)")
    public NoteDTO update(NoteUpdateDTO dto, Long id) {
        Note note = noteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));

        note.setTitle(dto.getTitle());
        note.setDescription(dto.getDescription());
        note.setUsername(dto.getUsername());
        note.setPassword(dto.getPassword());

        Note savedNote = noteRepository.save(note);
        return new NoteDTO(savedNote);
    }



    @PreAuthorize("@noteSecurity.isOwner(authentication, #id)")
    public void delete(Long id) {
        if (!noteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Note not found with id: " + id);
        }
        noteRepository.deleteById(id);
    }

}


