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
import static com.securevault.secure_vault_api.entities.enums.Role.ROLE_CLIENT;


@Configuration
@Profile("dev")
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
        User user3 = new User(null, "Julia", "julia@email.com", passwordEncoder.encode("123456"));
        user.roleAdd(ROLE_ADMIN);
        user2.roleAdd(ROLE_CLIENT);
        user3.roleAdd(ROLE_CLIENT);

        Note note  = new Note(null, "SteamPassword", user, LocalDateTime.now(), LocalDateTime.now(), "senha da conta steam", "123456987", "clay");
        Note note2  = new Note(null, "InstagramPassword", user2, LocalDateTime.now(), LocalDateTime.now(), "senha da conta do instagram", "123456987", "Lara");
        Note note3  = new Note(null, "InstagramPassword", user3, LocalDateTime.now(), LocalDateTime.now(), "senha da conta do instagram", "123456987", "Julia");

        userRepository.saveAll(Arrays.asList(user,user2, user3));
        noteRepository.saveAll(Arrays.asList(note,note2, note3));

    }
}
