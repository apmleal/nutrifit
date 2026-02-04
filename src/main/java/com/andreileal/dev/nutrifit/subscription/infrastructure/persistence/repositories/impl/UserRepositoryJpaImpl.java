package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.impl;

import com.andreileal.dev.nutrifit.subscription.domain.models.Plan;
import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;
import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.UserMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.PlanEntity;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.TenantEntity;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.UserEntity;
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
    public User createAccount(User user, Plan plan, Tenant tenant) {

        var planEntity = new PlanEntity(plan.getId());
        var tenantEntity = new TenantEntity(tenant.getId(), tenant.getNome().valor());

        var userEnttity = UserEntity.builder()
                .name(user.getNome().valor())
                .email(user.getEmail().valor())
                .password(user.getSenhaHasheada().hash())
                .plan(planEntity)
                .tenant(tenantEntity)
                .role(user.getRole())
                .active(user.isActive())
                .build();

        var userSaved = userRepositoryJpa.save(userEnttity);
        return UserMapper.toDomain(userSaved);
    }
}
