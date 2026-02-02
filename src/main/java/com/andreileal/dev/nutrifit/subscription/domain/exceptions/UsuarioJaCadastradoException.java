package com.andreileal.dev.nutrifit.subscription.domain.exceptions;

public class UsuarioJaCadastradoException extends DomainException {

    public UsuarioJaCadastradoException(String email) {
        super("Usuario ja encontrado: " + email);
    }

    public UsuarioJaCadastradoException() {
        super("Usuario ja encontrado");
    }
}
