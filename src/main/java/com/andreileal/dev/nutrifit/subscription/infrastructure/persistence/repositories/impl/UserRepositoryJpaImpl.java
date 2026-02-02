package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.impl;

import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.UserMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaUserRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJpaImpl implements UserRepository {

    private final JpaUserRepository userRepositoryJpa;

    public UserRepositoryJpaImpl(JpaUserRepository userRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepositoryJpa.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public User save(User user) {
        var userEntity = UserMapper.toEntity(user);
        var userSaved = userRepositoryJpa.save(userEntity);
        return UserMapper.toDomain(userSaved);
    }
}
