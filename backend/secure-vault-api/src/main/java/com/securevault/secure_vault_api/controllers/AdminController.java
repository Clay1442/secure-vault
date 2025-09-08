package com.securevault.secure_vault_api.controllers;

import com.securevault.secure_vault_api.dto.NoteMetadataDTO;
import com.securevault.secure_vault_api.services.AdminNoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value ="/api/admin/notes")
public class AdminController {

    private final AdminNoteService adminNoteService;

    public AdminController(AdminNoteService adminNoteService) {
        this.adminNoteService = adminNoteService;
    }

    @GetMapping
    public ResponseEntity<List<NoteMetadataDTO>> findAll() {
        List<NoteMetadataDTO> noteMetadataDTOS = adminNoteService.findAll();
        return ResponseEntity.ok(noteMetadataDTOS);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<NoteMetadataDTO>> findAllByUser(@PathVariable long userId) {
        List<NoteMetadataDTO> noteMetadataDTOS = adminNoteService.findAllByUserId(userId);
        return ResponseEntity.ok(noteMetadataDTOS);
    }

    @GetMapping("/{noteId}")
    public ResponseEntity<NoteMetadataDTO> findById(@PathVariable long noteId) {
        NoteMetadataDTO noteMetadataDTO = adminNoteService.findById(noteId);
        return ResponseEntity.ok(noteMetadataDTO);
    }

}
