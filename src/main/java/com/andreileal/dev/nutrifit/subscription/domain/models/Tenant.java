package com.andreileal.dev.nutrifit.subscription.domain.models;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;

import java.util.Objects;
import java.util.UUID;

public class Tenant {

    private UUID id;
    private Nome nome;
    private boolean active;

    private Tenant(UUID id, Nome nome, boolean active) {
        this.id = Objects.requireNonNull(id, "ID nao pode ser nulo");
        this.nome = Objects.requireNonNull(nome, "Nome nao pode ser nulo");
        this.active = active;
    }

    private Tenant(Nome nome, boolean active) {
        this.nome = Objects.requireNonNull(nome, "Nome nao pode ser nulo");
        this.active = active;
    }

    public static Tenant criar(Nome nome) {
        validarDadosCriacao(nome);
        return new Tenant(nome, true);
    }

    public static Tenant reconstituir(UUID id, Nome nome, boolean active) {
        return new Tenant(id, nome, active);
    }

    private static void validarDadosCriacao(Nome nome) {
        Objects.requireNonNull(nome, "Nome nao pode ser nulo");
    }

    public UUID getId() {
        return this.id;
    }

    public Nome getNome() {
        return this.nome;
    }

    public boolean isActive() {
        return this.active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Tenant user = (Tenant) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
