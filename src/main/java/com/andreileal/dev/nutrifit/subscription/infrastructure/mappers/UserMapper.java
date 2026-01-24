package com.andreileal.dev.nutrifit.subscription.infrastructure.mappers;


import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        SenhaHasheada senhaHasheada = new SenhaHasheada(entity.getPassword());

        return new User(entity.getId(), entity.getEmail(), entity.getName(), senhaHasheada);
    }

    public UserEntity toEntity(User domain) {
        if (domain == null) {
            return null;
        }

        UserEntity entity = new UserEntity();
        entity.setId(domain.getId());
        entity.setEmail(domain.getEmail());
        entity.setName(domain.getNome());
        entity.setPassword(domain.getSenhaHasheada().hash());
        
        return entity;
    }
}
