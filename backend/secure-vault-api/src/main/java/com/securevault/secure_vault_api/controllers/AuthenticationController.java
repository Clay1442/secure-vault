package com.securevault.secure_vault_api.controllers;

import com.securevault.secure_vault_api.dto.ForgotPasswordRequestDTO;
import com.securevault.secure_vault_api.dto.LoginRequestDTO;
import com.securevault.secure_vault_api.dto.LoginResponseDTO;
import com.securevault.secure_vault_api.dto.ResetPasswordDTO;
import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.services.AuthenticationService;
import com.securevault.secure_vault_api.services.TokenService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value ="/auth")
public class AuthenticationController {

    private final AuthenticationManager authenticationManager;
    private final TokenService tokenService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationManager authenticationManager,  TokenService tokenService,  AuthenticationService authenticationService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO dto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());

        var auth = this.authenticationManager.authenticate(usernamePassword);

        var user = (User) auth.getPrincipal();
        var token = tokenService.generateToken(user);

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody @Valid ForgotPasswordRequestDTO request) {
        authenticationService.createPasswordResetTokenForUser(request.getEmail());

        //return 200 ok, so as not to leak sensitive data
        return ResponseEntity.ok().build();
    }

    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@RequestBody @Valid ResetPasswordDTO request) {
        authenticationService.resetPassword(request.getToken(), request.getNewPassword());
        return ResponseEntity.ok().build();
    }


}
