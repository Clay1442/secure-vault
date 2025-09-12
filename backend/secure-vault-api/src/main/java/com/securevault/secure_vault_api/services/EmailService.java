package com.securevault.secure_vault_api.services;

import com.securevault.secure_vault_api.entities.User;
import com.securevault.secure_vault_api.exceptions.ErrorEmailSubmitException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final JavaMailSender mailSender;
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);

    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendPasswordResetEmail(User user, String token) {

        String subject = "Recuperação de Senha - Secure Vault";
        String text = "Olá, " + user.getName() + ",\n\n"
                + "Você solicitou a redefinição de sua senha. Por favor, clique no link abaixo para criar uma nova senha. Este link é válido por 15 minutos.\n\n"
                + "(Url para o FRONTEND que cuida do serviço de redefinição) token para teste " + token + "\n\n"
                + "Se você não solicitou esta alteração, por favor ignore este e-mail.\n\n"
                + "Atenciosamente,\nEquipe Secure Vault";

        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("no-reply@securevault.com");
            message.setTo(user.getEmail());
            message.setSubject(subject);
            message.setText(text);

            mailSender.send(message);
            logger.info("Reset E-mail submit to {}", user.getEmail());
        } catch (Exception e) {
            logger.error("Error for password reset to {}: {}", user.getEmail(), e.getMessage());
            throw new ErrorEmailSubmitException("Error for password reset to " + user.getEmail());
        }
    }
}