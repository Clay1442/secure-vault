package com.securevault.secure_vault_api.dto;

import com.securevault.secure_vault_api.entities.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class UserUpdateDTO {


    private String name;

    @Email(message = "Formato de email inválido")
    private String email;


    @Size(min = 6, message = "A senha deve ter no mínimo 6 caracteres")
    private String password;

    private String oldPassword;

    public UserUpdateDTO() {
    }


    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

}
