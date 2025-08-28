package com.securevault.secure_vault_api.repositories;

import com.securevault.secure_vault_api.entities.Note;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface NoteRepository extends JpaRepository<Note, Long> {

    Optional<Note> findByIdAndUserId(Long id, Long user_id);

    List<Note> findAllByUserId(Long userId);


}
