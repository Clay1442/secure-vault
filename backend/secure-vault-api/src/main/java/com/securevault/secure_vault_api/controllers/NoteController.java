package com.securevault.secure_vault_api.controllers;

import com.securevault.secure_vault_api.dto.NoteCreateDTO;
import com.securevault.secure_vault_api.dto.NoteDTO;
import com.securevault.secure_vault_api.dto.NoteUpdateDTO;
import com.securevault.secure_vault_api.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value ="/notes")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> findAll() {
        List<NoteDTO> notes = noteService.findAll();
        return ResponseEntity.ok().body(notes);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<NoteDTO> findById(@PathVariable long id) {
        NoteDTO notes = noteService.findById(id);
        return ResponseEntity.ok().body(notes);
    }

    @PostMapping
    public ResponseEntity<NoteDTO> create(@RequestBody NoteCreateDTO dto) {
        NoteDTO newNote = noteService.create(dto);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(newNote.getId()).toUri();
        return ResponseEntity.created(uri).body(newNote);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<NoteDTO> update(@RequestBody NoteUpdateDTO dto, @PathVariable long id) {
        NoteDTO note = noteService.update(dto, id);
        return ResponseEntity.ok().body(note);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
