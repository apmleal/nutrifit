package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.SenhaInvalidaException;

public record SenhaPlana(String plena) {

    private static final int TAMANHO_MINIMO = 6;

    public SenhaPlana {
        if (plena == null || plena.isBlank()) {
            throw new SenhaInvalidaException("Senha nao pode ser vazia");
        }

        if (plena.length() < TAMANHO_MINIMO) {
            throw new SenhaInvalidaException(
                    "Senha deve ter no minimo " + TAMANHO_MINIMO + " caracteres");
        }
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}