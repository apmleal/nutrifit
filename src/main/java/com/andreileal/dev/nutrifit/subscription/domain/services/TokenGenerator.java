package com.andreileal.dev.nutrifit.subscription.domain.services;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.AccessToken;

public interface TokenGenerator {

    AccessToken gerar(String email);

    String extrairEmail(String token);

    boolean validar(String token);
}
