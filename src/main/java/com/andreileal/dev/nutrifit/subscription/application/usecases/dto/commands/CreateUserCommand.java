package com.andreileal.dev.nutrifit.subscription.application.usecases.dto.commands;

import java.util.UUID;

public record CreateUserCommand(
        String email,
        String password,
        String name,
        UUID idPlan) {
    public CreateUserCommand {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email nao pode ser vazio");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password nao pode ser vazio");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Nome nao pode ser vazio");
        }
        if (idPlan == null) {
            throw new IllegalArgumentException("Plano nao pode ser vazio");
        }
    }
}
