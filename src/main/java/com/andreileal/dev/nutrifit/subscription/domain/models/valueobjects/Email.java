package com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects;

import java.util.regex.Pattern;

import com.andreileal.dev.nutrifit.subscription.domain.exceptions.EmailInvalidoException;

public record Email(String valor) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    public Email {
        if (valor == null || valor.isBlank()) {
            throw new EmailInvalidoException("Email nao pode ser vazio");
        }

        String emailTrimmed = valor.trim().toLowerCase();

        if (!EMAIL_PATTERN.matcher(emailTrimmed).matches()) {
            throw new EmailInvalidoException(valor);
        }

        valor = emailTrimmed;
    }

    @Override
    public String toString() {
        return valor;
    }
}
