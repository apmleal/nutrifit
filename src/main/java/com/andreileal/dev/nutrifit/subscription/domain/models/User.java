package com.andreileal.dev.nutrifit.subscription.domain.models;

import java.util.Objects;
import java.util.UUID;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;

public class User {

    private final UUID id;
    private Email email;
    private Nome nome;
    private final SenhaHasheada senhaHasheada;

    private User(UUID id, Email email, Nome nome, SenhaHasheada senhaHasheada) {
        this.id = Objects.requireNonNull(id, "ID nao pode ser nulo");
        this.email = Objects.requireNonNull(email, "Email nao pode ser nulo");
        this.nome = Objects.requireNonNull(nome, "Nome nao pode ser nulo");
        this.senhaHasheada = Objects.requireNonNull(senhaHasheada, "Senha nao pode ser nula");
    }

    public static User criar(Email email, Nome nome, SenhaPlana senhaPlana, PasswordHasher passwordHasher) {
        validarDadosCriacao(email, nome, senhaPlana);
        SenhaHasheada senhaHasheada = passwordHasher.hash(senhaPlana);
        return new User(UUID.randomUUID(), email, nome, senhaHasheada);
    }

    public static User reconstituir(UUID id, Email email, Nome nome, SenhaHasheada senhaHasheada) {
        return new User(id, email, nome, senhaHasheada);
    }

    private static void validarDadosCriacao(Email email, Nome nome, SenhaPlana senhaPlana) {
        Objects.requireNonNull(email, "Email nao pode ser nulo");
        Objects.requireNonNull(nome, "Nome nao pode ser nulo");
        Objects.requireNonNull(senhaPlana, "Senha nao pode ser nula");
    }

    public User alterarEmail(Email novoEmail) {
        Objects.requireNonNull(novoEmail, "Novo email nao pode ser nulo");
        this.email = novoEmail;
        return this;
    }

    public User atualizarNome(Nome novoNome) {
        Objects.requireNonNull(novoNome, "Novo nome nao pode ser nulo");
        this.nome = novoNome;
        return this;
    }

    public boolean isPasswordValid(SenhaPlana senhaPlana, PasswordHasher passwordHasher) {
        return passwordHasher.matches(senhaPlana, this.senhaHasheada);
    }

    public UUID getId() {
        return this.id;
    }

    public Email getEmail() {
        return this.email;
    }

    public Nome getNome() {
        return this.nome;
    }

    public SenhaHasheada getSenhaHasheada() {
        return this.senhaHasheada;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
