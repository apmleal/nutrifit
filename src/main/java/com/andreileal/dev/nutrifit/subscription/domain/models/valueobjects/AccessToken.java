package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

public record AccessToken(String valor) {

    public AccessToken {
        if (valor == null || valor.isBlank()) {
            throw new IllegalArgumentException("Token nao pode ser vazio");
        }
    }

    @Override
    public String toString() {
        return valor;
    }
}