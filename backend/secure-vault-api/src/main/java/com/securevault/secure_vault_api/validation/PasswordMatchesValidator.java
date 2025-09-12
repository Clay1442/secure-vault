package com.securevault.secure_vault_api.validation;

import com.securevault.secure_vault_api.dto.ResetPasswordDTO;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, ResetPasswordDTO> {

    @Override
    public boolean isValid(ResetPasswordDTO dto, ConstraintValidatorContext context) {
        if (dto.getNewPassword() == null || dto.getConfirmPassword() == null) {
            return false;
        }
        return dto.getNewPassword().equals(dto.getConfirmPassword());
    }

}
