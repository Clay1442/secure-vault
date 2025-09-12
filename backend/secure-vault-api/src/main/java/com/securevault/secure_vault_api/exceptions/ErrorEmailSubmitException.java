package com.securevault.secure_vault_api.exceptions;

public class ErrorEmailSubmitException extends RuntimeException {
    private static final long serialVersionUID = 1L;
    public ErrorEmailSubmitException(String message) {
        super(message);
    }
}
