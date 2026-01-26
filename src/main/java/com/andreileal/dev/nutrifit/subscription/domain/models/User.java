package com.andreileal.dev.nutrifit.subscription.domain.models;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;

import java.util.UUID;

public class User {

    private UUID id;
    private String email;
    private String nome;
    private final SenhaHasheada senhaHasheada;

    public User(UUID id, String email, String nome, SenhaHasheada senhaHasheada) {
        this.id = id;
        this.email = email;
        this.nome = nome;
        this.senhaHasheada = senhaHasheada;
    }

    public UUID getId() {
        return this.id;
    }

    public String getEmail() {
        return this.email;
    }

    public String getNome() {
        return this.nome;
    }

    public SenhaHasheada getSenhaHasheada() {
        return this.senhaHasheada;
    }

    public boolean isPassworValid(SenhaPlana senhaPlana, PasswordHasher passwordHasher) {
        return passwordHasher.matches(senhaPlana, this.senhaHasheada);
    }
}
