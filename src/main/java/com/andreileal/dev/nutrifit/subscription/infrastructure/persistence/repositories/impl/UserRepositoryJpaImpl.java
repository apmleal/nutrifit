package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.impl;

import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;
import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.UserMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaTenantRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaUserRepository;

@Repository
public class UserRepositoryJpaImpl implements UserRepository {

    private final JpaUserRepository userRepositoryJpa;
    private final JpaTenantRepository tenantRepositoryJpa;

    public UserRepositoryJpaImpl(JpaUserRepository userRepositoryJpa, JpaTenantRepository tenantRepositoryJpa) {
        this.userRepositoryJpa = userRepositoryJpa;
        this.tenantRepositoryJpa = tenantRepositoryJpa;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepositoryJpa.findByEmail(email)
                .map(UserMapper::toDomain);
    }

    @Override
    public User createAccount(User user, Tenant tenant) {
        var tenantEntity = tenantRepositoryJpa.getReferenceById(tenant.getId());

        var userEntity = UserMapper.toEntity(user);
        userEntity.setTenant(tenantEntity);

        var userSaved = userRepositoryJpa.save(userEntity);
        return UserMapper.toDomain(userSaved);
    }
}
