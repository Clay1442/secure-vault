package com.securevault.secure_vault_api.config;

import com.securevault.secure_vault_api.entities.Note;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.repositories.NoteRepository;
import com.securevault.secure_vault_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.Arrays;
import java.time.LocalDateTime;

import static com.securevault.secure_vault_api.entities.enums.Role.ROLE_ADMIN;


@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner{

    @Autowired
    UserRepository userRepository;


    @Autowired
    NoteRepository noteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void run(String... args) throws Exception {
        User user = new User(null, "clay", "clay@email.com", passwordEncoder.encode("123456"));
        User user2 = new User(null, "Lara", "lara@email.com", passwordEncoder.encode("123456"));
        user.roleAdd(ROLE_ADMIN);
        user2.roleAdd(ROLE_ADMIN);

        Note note  = new Note(null, "SteamPassword", user, LocalDateTime.now(), LocalDateTime.now(), "senha da conta steam", "123456987", "clay");
        Note note2  = new Note(null, "InstagramPassword", user2, LocalDateTime.now(), LocalDateTime.now(), "senha da conta do instagram", "123456987", "Lara");

        userRepository.saveAll(Arrays.asList(user,user2));
        noteRepository.saveAll(Arrays.asList(note,note2));

    }
}
