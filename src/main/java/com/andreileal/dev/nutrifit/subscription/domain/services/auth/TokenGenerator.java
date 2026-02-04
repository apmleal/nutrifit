package com.andreileal.dev.nutrifit.subscription.domain.services.auth;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.AccessToken;

import java.util.UUID;

public interface TokenGenerator {

    AccessToken gerar(String email, UUID tenantId, String role);

    String extrairEmail(String token);

    UUID extrairIdTenant(String token);

    String extrairRole(String token);

    boolean validar(String token);
}
