package com.andreileal.dev.nutrifit.subscription.application.dto.results;

import java.util.UUID;

public record SelectAccountResult(
        UUID userId,
        String email,
        String nome,
        String token) {
    public SelectAccountResult {
        if (token == null || token.isBlank()) {
            throw new IllegalArgumentException("Token nao pode ser nulo");
        }
        if (userId == null) {
            throw new IllegalArgumentException("UserId nao pode ser nulo");
        }
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email nao pode ser vazio");
        }
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("Nome nao pode ser vazio");
        }
    }
}
