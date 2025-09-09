package com.securevault.secure_vault_api.services;

import com.securevault.secure_vault_api.dto.NoteMetadataDTO;
import com.securevault.secure_vault_api.entities.Note;
import com.securevault.secure_vault_api.exceptions.ResourceNotFoundException;
import com.securevault.secure_vault_api.repositories.NoteRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminNoteService {

    private final NoteRepository noteRepository;

    public AdminNoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    public NoteMetadataDTO findById(Long id) {
        Note note = noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note note found"));
        return new NoteMetadataDTO(note);
    }

    @PreAuthorize("hasRole('ADMIN')")
    public List<NoteMetadataDTO> findAll() {
        return noteRepository.findAll()
                .stream()
                .map(NoteMetadataDTO::new)
                .collect(Collectors.toList());
    }


    @PreAuthorize("hasRole('ADMIN')")
    public List<NoteMetadataDTO> findNotesByUserId(Long id) {
        return noteRepository.findAllByUserId(id)
                .stream()
                .map(NoteMetadataDTO::new)
                .collect(Collectors.toList());

    }


}

