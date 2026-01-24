package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

public record SenhaPlana(String plena) {

    private static final int TAMANHO_MINIMO = 6;

    public SenhaPlana {
        if (plena == null || plena.isBlank()) {
            throw new IllegalArgumentException("Senha nao pode ser vazia");
        }

        if (plena.length() < TAMANHO_MINIMO) {
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