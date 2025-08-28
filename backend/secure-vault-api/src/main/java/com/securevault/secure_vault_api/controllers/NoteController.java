package com.securevault.secure_vault_api.controllers;

import com.securevault.secure_vault_api.dto.NoteDTO;
import com.securevault.secure_vault_api.services.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/note")
public class NoteController {

    @Autowired
    private NoteService noteService;

    @GetMapping
    public ResponseEntity<List<NoteDTO>> findAll(){
       List<NoteDTO> notes =  noteService.findAll();
       return ResponseEntity.ok().body(notes);
    }

}
