package com.securevault.secure_vault_api.dto;

import com.securevault.secure_vault_api.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserCreateDTO {

    @NotBlank(message = "O nome não pode ser vazio")
    private String name;

    @Email(message = "Formato de email inválido")
    private String email;

    @NotBlank
    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    public UserCreateDTO() {
    }

    public UserCreateDTO(User entity) {
        super();
        this.name = entity.getName();
        this.email = entity.getEmail();
        this.password = entity.getPassword();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
