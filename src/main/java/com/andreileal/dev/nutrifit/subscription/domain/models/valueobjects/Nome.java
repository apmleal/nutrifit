package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.NomeInvalidoException;

public record Nome(String valor) {

    private static final int TAMANHO_MINIMO = 3;
    private static final int TAMANHO_MAXIMO = 200;

    public Nome {
        if (valor == null || valor.isBlank()) {
            throw new NomeInvalidoException("Nome nao pode ser vazio");
        }

        String nomeTrimmed = valor.trim();

        if (nomeTrimmed.length() < TAMANHO_MINIMO) {
            throw new NomeInvalidoException(
                    "Nome deve ter no minimo " + TAMANHO_MINIMO + " caracteres");
        }

        if (nomeTrimmed.length() > TAMANHO_MAXIMO) {
            throw new NomeInvalidoException(
                    "Nome deve ter no maximo " + TAMANHO_MAXIMO + " caracteres");
        }

        valor = nomeTrimmed;
    }

    @Override
    public String toString() {
        return valor;
    }
}
