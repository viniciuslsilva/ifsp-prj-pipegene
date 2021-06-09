package br.edu.ifsp.scl.pipegene.configuration.security.jwt;

import br.edu.ifsp.scl.pipegene.configuration.properties.model.JwtProperties;
import io.jsonwebtoken.security.Keys;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.crypto.SecretKey;

@Configuration
public class JwtSecretKey {

    private final JwtProperties properties;

    public JwtSecretKey(JwtProperties properties) {
        this.properties = properties;
    }

    @Bean
    public SecretKey secretKey() {
        return Keys.hmacShaKeyFor(properties.getSecretKey() .getBytes());
    }

}
