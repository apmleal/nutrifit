package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.impl;

import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.UserMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.UserRepositoryJpa;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserRepositoryJpaImpl implements UserRepository {

    private final UserMapper userMapper;
    private final UserRepositoryJpa userRepositoryJpa;

    public UserRepositoryJpaImpl(UserMapper userMapper, UserRepositoryJpa userRepositoryJpa) {
        this.userMapper = userMapper;
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepositoryJpa.findByEmail(email)
                .map(userMapper::toDomain);
    }
}
