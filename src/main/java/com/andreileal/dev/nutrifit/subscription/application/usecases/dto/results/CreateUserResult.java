package com.andreileal.dev.nutrifit.subscription.application.usecases.dto.results;

import java.util.UUID;

public record CreateUserResult(
        UUID userId,
        String email,
        String nome) {
    public CreateUserResult {
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
