package com.andreileal.dev.nutrifit.subscription.domain.exceptions;

public class CredenciaisInvalidasException extends DomainException {

    public CredenciaisInvalidasException() {
        super("Credenciais invalidas");
    }

    public CredenciaisInvalidasException(String mensagem) {
        super(mensagem);
    }
}
