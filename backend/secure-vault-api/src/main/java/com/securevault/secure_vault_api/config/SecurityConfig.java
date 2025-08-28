package com.securevault.secure_vault_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;


@Configuration
public class SecurityConfig {


 @Bean
 public CorsConfigurationSource corsConfigurationSource() {

 CorsConfiguration corsConfiguration = new CorsConfiguration();
 corsConfiguration.addAllowedOrigin("http://localhost:8080/h2-console");

 UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
 source.registerCorsConfiguration("/**", corsConfiguration);
 return source;

 }

 @Bean
 public PasswordEncoder passwordEncoder(){
     return new BCryptPasswordEncoder();
 }

}
