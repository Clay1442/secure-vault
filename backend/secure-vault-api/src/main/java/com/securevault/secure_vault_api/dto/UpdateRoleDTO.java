package com.securevault.secure_vault_api.dto;

import com.securevault.secure_vault_api.entities.enums.Role;

public class UpdateRoleDTO {


    private Role roles;

    public Role getRoles() {
        return roles;
    }

    public void setRoles(Role roles) {
        this.roles = roles;
    }
}
