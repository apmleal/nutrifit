package com.andreileal.dev.nutrifit.subscription.domain.exceptions;

public class EmailInvalidoException extends DomainException {

    public EmailInvalidoException(String email) {
        super("Email invalido: " + email);
    }

    public EmailInvalidoException(String mensagem, Throwable cause) {
        super(mensagem, cause);
    }
}
