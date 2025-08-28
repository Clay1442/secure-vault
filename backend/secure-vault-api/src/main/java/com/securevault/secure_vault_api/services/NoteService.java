package com.securevault.secure_vault_api.services;


import com.securevault.secure_vault_api.dto.NoteCreateDTO;
import com.securevault.secure_vault_api.dto.NoteDTO;
import com.securevault.secure_vault_api.dto.NoteUpdateDTO;
import com.securevault.secure_vault_api.entities.Note;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.exceptions.ResourceNotFoundException;
import com.securevault.secure_vault_api.repositories.NoteRepository;
import com.securevault.secure_vault_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    public NoteDTO findById(Long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found"));

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id + " and userId: " + user.getId()));
        return new NoteDTO(note);
    }

    public List<NoteDTO> findAll() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentPrincipalName));

       List<Note> notes =  noteRepository.findAllByUserId(user.getId());
       return notes.stream().map(NoteDTO::new).collect(Collectors.toList());
    }


    public NoteDTO create(NoteCreateDTO dto){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(()-> new ResourceNotFoundException("User not found with email: " + currentPrincipalName));

        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setDescription(dto.getDescription());
        note.setUsername(dto.getUsername());
        note.setPassword(dto.getPassword());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);

        return new NoteDTO(savedNote);
    }

    public NoteDTO update(NoteUpdateDTO dto, long id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        User user = userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentPrincipalName));

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id + " and userId: " + user.getId()));

        if(!note.getUser().getEmail().equals(currentPrincipalName)){
            throw new AccessDeniedException("You do not have permission to modify this note.");
        }

        note.setTitle(dto.getTitle());
        note.setDescription(dto.getDescription());
        note.setUsername(dto.getUsername());
        note.setPassword(dto.getPassword());

        Note savedNote = noteRepository.save(note);
        return new NoteDTO(savedNote);
    }


    public void delete(Long id) {
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       String currentPrincipalName = authentication.getName();

        User user = userRepository.findByEmail(currentPrincipalName)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + currentPrincipalName));

        Note note = noteRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Note not found with id: " + id + " for user: " + user.getId()
                ));
        noteRepository.delete(note);
    }


}
