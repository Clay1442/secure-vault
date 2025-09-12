package com.securevault.secure_vault_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
public class SecureVaultApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecureVaultApiApplication.class, args);
	}

}
