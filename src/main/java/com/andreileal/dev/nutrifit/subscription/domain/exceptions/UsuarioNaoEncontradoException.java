package com.andreileal.dev.nutrifit.subscription.domain.exceptions;

public class UsuarioNaoEncontradoException extends DomainException {

    public UsuarioNaoEncontradoException(String email) {
        super("Usuario nao encontrado: " + email);
    }

    public UsuarioNaoEncontradoException() {
        super("Usuario nao encontrado");
    }
}
