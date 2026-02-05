package com.andreileal.dev.nutrifit.subscription.presentation.dtos.responses;

import com.andreileal.dev.nutrifit.subscription.application.dto.results.LoginResult;

import java.util.UUID;

/**
 * DTO de resposta para autentica��o bem-sucedida.
 * Cont�m o token de acesso e informa��es do usu�rio.
 */
public record LoginResponseDto(
        String token,
        UserInfoDto user) {
    public static LoginResponseDto fromLoginResult(LoginResult result) {
        return new LoginResponseDto(
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