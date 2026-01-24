package com.andreileal.dev.nutrifit.subscription.domain.repositories;

import com.andreileal.dev.nutrifit.subscription.domain.models.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findUserByEmail(String email);
}
