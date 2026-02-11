package com.andreileal.dev.nutrifit.subscription.domain.repositories;

import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;
import com.andreileal.dev.nutrifit.subscription.domain.models.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository {
    Optional<User> findUserByEmail(String email);

    User createAccount(User user, Tenant tenant);

    Optional<User> findByEmailWithTenants(String email);

    Optional<User> findUserById(UUID id);

    User findUserByIdAndIdTenant(UUID id, UUID tenant);
}
