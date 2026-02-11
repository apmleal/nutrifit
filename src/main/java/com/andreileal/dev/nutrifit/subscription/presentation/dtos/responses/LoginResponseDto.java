package com.andreileal.dev.nutrifit.subscription.presentation.dtos.responses;

import com.andreileal.dev.nutrifit.subscription.application.dto.results.LoginResult;

import java.util.List;
import java.util.UUID;

/**
 * DTO de resposta para autentica��o bem-sucedida.
 * Cont�m o token de acesso e informa��es do usu�rio.
 */
public record LoginResponseDto(
        UUID id,
        String email,
        String nome,
        List<UUID> tenants) {
    public static LoginResponseDto fromLoginResult(LoginResult result) {
        return new LoginResponseDto(
                result.userId(),
                result.email(),
                result.nome(),
                result.tenants()
        );
    }

}