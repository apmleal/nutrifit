package com.andreileal.dev.nutrifit.subscription.application.usecases.dto.commands;

public record CreateUserCommand(
        String email,
        String password,
        String name) {
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
    }
}
