package com.andreileal.dev.nutrifit.subscription.application.usecases.dto.commands;

public record LoginCommand(
        String email,
        String password) {
    public LoginCommand {
        if (email == null || email.isBlank()) {
            throw new IllegalArgumentException("Email nao pode ser vazio");
        }
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("Password nao pode ser vazio");
        }
    }
}
