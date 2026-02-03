package com.andreileal.dev.nutrifit.subscription.domain.models;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;

import java.util.Objects;
import java.util.UUID;

public class Plan {

    private final UUID id;
    private Nome nome;


    private Plan(UUID id, Nome nome) {
        this.id = Objects.requireNonNull(id, "ID nao pode ser nulo");
        this.nome = Objects.requireNonNull(nome, "Nome nao pode ser nulo");
    }

    public static Plan criar(Nome nome) {
        validarDadosCriacao(nome);
        return new Plan(UUID.randomUUID(), nome);
    }

    public static Plan reconstituir(UUID id, Nome nome) {
        return new Plan(id, nome);
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

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Plan user = (Plan) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
