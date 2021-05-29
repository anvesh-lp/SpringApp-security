package com.anvesh.springsecurity.jwt;


import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwrSecretKey {

    private final JwtConfig config;

    public JwrSecretKey(JwtConfig config) {
        this.config = config;
    }

    @Bean
    public SecretKey getSecretKey() {
        return Keys.hmacShaKeyFor(config.getSecretkey().getBytes());
    }
}
