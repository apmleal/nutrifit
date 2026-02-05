package com.andreileal.dev.nutrifit.subscription.infrastructure.mappers;

import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.UserEntity;

public class UserMapper {

    private UserMapper() {

    }

    public static User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }

        Email email = new Email(entity.getEmail());
        Nome nome = new Nome(entity.getName());
        SenhaHasheada senhaHasheada = new SenhaHasheada(entity.getPassword());
        return User.reconstituir(entity.getId(), email, nome, senhaHasheada, entity.getTenant().getId(),
                entity.getRole(), entity.isActive());
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        // Não setar o ID - deixar o Hibernate gerar via @GeneratedValue
        entity.setEmail(domain.getEmail().valor());
        entity.setName(domain.getNome().valor());
        entity.setPassword(domain.getSenhaHasheada().hash());
        entity.setRole(domain.getRole());
        entity.setActive(domain.isActive());

        return entity;
    }
}
