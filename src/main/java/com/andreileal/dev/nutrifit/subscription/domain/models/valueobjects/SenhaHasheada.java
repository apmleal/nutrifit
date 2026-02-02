package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.SenhaInvalidaException;

public record SenhaHasheada(String hash) {

    private static final int TAMANHO_MINIMO_BCRYPT = 59;

    public SenhaHasheada {
        if (hash == null || hash.isBlank()) {
            throw new SenhaInvalidaException("Hash da senha nao pode ser vazio");
        }

        // Validacao de formato bcrypt
        // Bcrypt sempre comeca com $2a$, $2b$, ou $2y$ e tem 60 caracteres
        if (!hash.startsWith("$2a$") && !hash.startsWith("$2b$") && !hash.startsWith("$2y$")) {
            throw new SenhaInvalidaException(
                    "Hash deve estar no formato bcrypt (iniciando com $2a$, $2b$ ou $2y$)");
        }

        if (hash.length() < TAMANHO_MINIMO_BCRYPT) {
            throw new SenhaInvalidaException(
                    "Hash bcrypt deve ter no minimo " + TAMANHO_MINIMO_BCRYPT + " caracteres");
        }
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}