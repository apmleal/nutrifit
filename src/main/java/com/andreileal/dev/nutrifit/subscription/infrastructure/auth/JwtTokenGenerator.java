package com.andreileal.dev.nutrifit.subscription.infrastructure.auth;


import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.AccessToken;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.TokenGenerator;
import com.andreileal.dev.nutrifit.subscription.infrastructure.conts.ParamTenantId;
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
    public AccessToken gerar(String email, UUID idTenant) {
        String token = Jwts.builder()
                .subject(email)
                .claim(ParamTenantId.ID_TENANT, idTenant)
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
        return claims.get(ParamTenantId.ID_TENANT, UUID.class);
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
