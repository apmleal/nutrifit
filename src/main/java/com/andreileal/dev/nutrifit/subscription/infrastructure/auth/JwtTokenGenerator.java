package com.andreileal.dev.nutrifit.subscription.infrastructure.auth;


import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.AccessToken;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.TokenGenerator;
import com.andreileal.dev.nutrifit.subscription.infrastructure.conts.ClaimsTypeJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Component
@Slf4j
public class JwtTokenGenerator implements TokenGenerator {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private int jwtExpirationMs;

    private SecretKey key;

    @PostConstruct
    public void init() {
        this.key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public AccessToken gerar(String email, UUID idTenant, String role) {
        String token = Jwts.builder()
                .subject(email)
                .claim(ClaimsTypeJWT.ID_TENANT, idTenant)
                .claim(ClaimsTypeJWT.ROLE, role)
                .issuedAt(new Date())
                .expiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(key)
                .compact();

        return new AccessToken(token);
    }

    @Override
    public String extrairEmail(String token) {
        return extractAllProperties(token)
                .getSubject();
    }

    @Override
    public UUID extrairIdTenant(String token) {
        Claims claims = extractAllProperties(token);
        var id_tenant = claims.get(ClaimsTypeJWT.ID_TENANT, String.class);
        return UUID.fromString(id_tenant);
    }

    @Override
    public String extrairRole(String token) {
        Claims claims = extractAllProperties(token);
        return claims.get(ClaimsTypeJWT.ROLE, String.class);
    }

    @Override
    public boolean validar(String token) {
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            log.error("JWT validation error: {}", e.getMessage());
            return false;
        }
    }

    private Claims extractAllProperties(String token) {
        return Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
