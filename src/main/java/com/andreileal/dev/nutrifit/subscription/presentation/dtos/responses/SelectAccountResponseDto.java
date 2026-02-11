package com.andreileal.dev.nutrifit.subscription.presentation.dtos.responses;

import com.andreileal.dev.nutrifit.subscription.application.dto.results.SelectAccountResult;

import java.util.UUID;

/**
 * DTO de resposta para autentica��o bem-sucedida.
 * Cont�m o token de acesso e informa��es do usu�rio.
 */
public record SelectAccountResponseDto(
        String token,
        UserInfoDto user) {
    public static SelectAccountResponseDto fromLoginResult(SelectAccountResult result) {
        return new SelectAccountResponseDto(
                result.token(),
                new UserInfoDto(
                        result.userId(),
                        result.email(),
                        result.nome()));
    }

    public record UserInfoDto(
            UUID id,
            String email,
            String nome) {
    }
}