package com.securevault.secure_vault_api.exceptions;

public class ExistingObject extends RuntimeException {
    public ExistingObject(String message) {
        super(message);
    }
}
