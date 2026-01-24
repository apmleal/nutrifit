package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

public record SenhaHasheada(String hash) {

    private static final int TAMANHO_MINIMO = 6;

    public SenhaHasheada {
        if (hash == null || hash.isBlank()) {
            throw new IllegalArgumentException("Senha nao pode ser vazia");
        }

        if (hash.length() < TAMANHO_MINIMO) {
            throw new IllegalArgumentException(
                    "Senha deve ter no minimo " + TAMANHO_MINIMO + " caracteres"
            );
        }
    }

    @Override
    public String toString() {
        return "[PROTECTED]";
    }
}