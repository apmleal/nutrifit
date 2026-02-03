package com.andreileal.dev.nutrifit.subscription.domain.services.auth;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.AccessToken;

import java.util.UUID;

public interface TokenGenerator {

    AccessToken gerar(String email, UUID tenantId);

    String extrairEmail(String token);

    UUID extrairIdTenant(String token);

    boolean validar(String token);
}
