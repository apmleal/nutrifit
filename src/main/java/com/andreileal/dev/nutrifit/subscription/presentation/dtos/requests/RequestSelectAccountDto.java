package com.andreileal.dev.nutrifit.subscription.presentation.dtos.requests;

import jakarta.validation.constraints.NotBlank;

import java.util.UUID;

/**
 * DTO para requisi??o de autentica??o.
 * Cont?m valida??es de entrada para garantir dados m?nimos v?lidos.
 */
public record RequestSelectAccountDto(
        @NotBlank(message = "Identificador do usuário é obrigatório") UUID usuario,
        @NotBlank(message = "Identificador do tenant é obrigatório") UUID account) {
}
