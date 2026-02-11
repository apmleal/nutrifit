package com.andreileal.dev.nutrifit.nutrition.presentation.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record RequestCreateUserDto(
        @NotBlank(message = "Email e obrigatorio") @Email(message = "Email invalido") String email,
        @NotBlank(message = "Senha e obrigatoria") @Size(min = 6, message = "Senha deve ter no minimo 6 caracteres") String senha,
        @NotBlank(message = "O nome e obrigatorio") @Size(min = 2, message = "O nome deve ter o minimo 2 caracteres") String name,
        @NotBlank(message = "O nome e obrigatorio") UUID idPlan) {
}
