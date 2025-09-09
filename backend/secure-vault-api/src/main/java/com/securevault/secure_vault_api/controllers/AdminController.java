package com.securevault.secure_vault_api.controllers;

import com.securevault.secure_vault_api.dto.NoteMetadataDTO;
import com.securevault.secure_vault_api.dto.UpdateRoleDTO;
import com.securevault.secure_vault_api.dto.UserDTO;
import com.securevault.secure_vault_api.services.AdminNoteService;
import com.securevault.secure_vault_api.services.AdminUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value ="/api/admin")
public class AdminController {

    private final AdminNoteService adminNoteService;
    private final AdminUserService adminUserService;

    public AdminController(AdminNoteService adminNoteService,  AdminUserService adminUserService) {
        this.adminNoteService = adminNoteService;
        this.adminUserService = adminUserService;
    }

    @GetMapping("/notes")
    public ResponseEntity<List<NoteMetadataDTO>> findAll() {
        List<NoteMetadataDTO> noteMetadataDTOS = adminNoteService.findAll();
        return ResponseEntity.ok(noteMetadataDTOS);
    }

    @GetMapping("/notes/user/{userId}")
    public ResponseEntity<List<NoteMetadataDTO>> findAllByUserId(@PathVariable long userId) {
        List<NoteMetadataDTO> noteMetadataDTOS = adminNoteService.findNotesByUserId(userId);
        return ResponseEntity.ok(noteMetadataDTOS);
    }

    @GetMapping("/notesId/{noteId}")
    public ResponseEntity<NoteMetadataDTO> findById(@PathVariable long noteId) {
        NoteMetadataDTO noteMetadataDTO = adminNoteService.findById(noteId);
        return ResponseEntity.ok(noteMetadataDTO);
    }

    @PatchMapping("/role-update/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id, @RequestBody UpdateRoleDTO dto) {
        UserDTO userUpdate = adminUserService.updateUserRole(dto, id);
        return ResponseEntity.ok(userUpdate);
    }



}
