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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class NoteService {

    @Autowired
    private NoteRepository noteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    protected Note findByEntity(Long id){
         return noteRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Note not found with id: " + id));
    }

    public NoteDTO findById(Long id){
        Note note = findByEntity(id);
        return new NoteDTO(note);
    }

    public List<NoteDTO> findAll(){
        List<Note> notes = noteRepository.findAll();
        List<NoteDTO> dtos = notes.stream().map(NoteDTO::new).collect(Collectors.toList());
        return dtos;
    }


    public NoteDTO create(NoteCreateDTO dto, long id){
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        Note note = new Note();
        note.setTitle(dto.getTitle());
        note.setDescription(dto.getDescription());
        note.setUsername(dto.getUsername());
        note.setPassword(dto.getPassword());
        note.setUser(user);

        Note savedNote = noteRepository.save(note);

        return new NoteDTO(savedNote);
    }

    public NoteDTO update(NoteUpdateDTO dto, long id){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();

        Note note = findByEntity(id);

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


    public void delete(Long id){
        Note note = findByEntity(id);
        noteRepository.deleteById(note.getId());
    }


}
