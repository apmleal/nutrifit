package com.andreileal.dev.nutrifit.subscription.presentation.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * DTO para requisição de autenticação.
 * Contém validações de entrada para garantir dados mínimos válidos.
 */
public record RequestAutenticacaoDto(
        @NotBlank(message = "Email e obrigatorio") @Email(message = "Email invalido") String email,

        @NotBlank(message = "Senha e obrigatoria") @Size(min = 6, message = "Senha deve ter no minimo 6 caracteres") String senha) {
}
