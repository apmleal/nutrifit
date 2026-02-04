package com.andreileal.dev.nutrifit.subscription.domain.models;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;

import java.util.Objects;
import java.util.UUID;

public class User {

    private final UUID id;
    private Email email;
    private Nome nome;
    private UUID idTenant;
    private final SenhaHasheada senhaHasheada;
    private Role role;
    private boolean active;

    private User(UUID id, Email email, Nome nome, SenhaHasheada senhaHasheada, UUID idTenant, Role role, boolean active) {
        this.id = Objects.requireNonNull(id, "ID nao pode ser nulo");
        this.email = Objects.requireNonNull(email, "Email nao pode ser nulo");
        this.nome = Objects.requireNonNull(nome, "Nome nao pode ser nulo");
        this.senhaHasheada = Objects.requireNonNull(senhaHasheada, "Senha nao pode ser nula");
        this.role = Objects.requireNonNull(role, "Role nao pode ser nulo");
        this.active = active;
        this.idTenant = idTenant;
    }

    public static User criar(Email email, Nome nome, SenhaPlana senhaPlana,
                             PasswordHasher passwordHasher, UUID idTenant, Role role, boolean active) {
        validarDadosCriacao(email, nome, senhaPlana);
        SenhaHasheada senhaHasheada = passwordHasher.hash(senhaPlana);
        return new User(UUID.randomUUID(), email, nome, senhaHasheada, idTenant, role, active);
    }

    public static User reconstituir(UUID id, Email email, Nome nome, SenhaHasheada senhaHasheada, UUID idTenant, Role role, boolean active) {
        return new User(id, email, nome, senhaHasheada, idTenant, role, active);
    }

    private static void validarDadosCriacao(Email email, Nome nome, SenhaPlana senhaPlana) {
        Objects.requireNonNull(email, "Email nao pode ser nulo");
        Objects.requireNonNull(nome, "Nome nao pode ser nulo");
        Objects.requireNonNull(senhaPlana, "Senha nao pode ser nula");
    }

    public User alterarEmail(Email novoEmail) {
        Objects.requireNonNull(novoEmail, "Novo email nao pode ser nulo");
        return new User(this.id, novoEmail, this.nome, this.senhaHasheada, this.idTenant, this.role, this.active);
    }

    public User atualizarNome(Nome novoNome) {
        Objects.requireNonNull(novoNome, "Novo nome nao pode ser nulo");
        return new User(this.id, this.email, novoNome, this.senhaHasheada, this.idTenant, this.role, this.active);
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

    public UUID getIdTenant() {
        return this.idTenant;
    }

    public Role getRole() {
        return this.role;
    }

    public SenhaHasheada getSenhaHasheada() {
        return this.senhaHasheada;
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
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
