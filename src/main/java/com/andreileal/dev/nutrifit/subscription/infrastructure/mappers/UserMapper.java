package com.andreileal.dev.nutrifit.subscription.infrastructure.mappers;

import java.util.List;
import java.util.UUID;

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
        var tenants = entity.getUserTenants().stream().map(t -> t.getTenant().getId()).toList();

        return User.reconstituir(entity.getId(), email, nome, senhaHasheada, tenants,
                null, entity.isActive());
    }

    public static User toDomainOneAccount(UserEntity userEntity, UUID idTenant) {
        if (userEntity == null || idTenant == null) {
            return null;
        }

        Email email = new Email(userEntity.getEmail());
        Nome nome = new Nome(userEntity.getName());
        SenhaHasheada senhaHasheada = new SenhaHasheada(userEntity.getPassword());
        var tenant = userEntity.getUserTenants().stream().filter(t -> t.getTenant().getId().equals(idTenant))
                .findFirst().get();

        return User.reconstituir(userEntity.getId(), email, nome, senhaHasheada, List.of(tenant.getId()),
                tenant.getRole(), userEntity.isActive());
    }

    public static UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setEmail(domain.getEmail().valor());
        entity.setName(domain.getNome().valor());
        entity.setPassword(domain.getSenhaHasheada().hash());
        entity.setActive(domain.isActive());

        return entity;
    }
}
