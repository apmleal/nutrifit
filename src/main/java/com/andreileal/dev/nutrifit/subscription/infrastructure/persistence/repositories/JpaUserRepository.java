package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories;

import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> {
    Optional<UserEntity> findByEmail(String email);

    @EntityGraph(attributePaths = {"userTenants", "userTenants.tenant"})
    @Query("SELECT u FROM UserEntity u WHERE u.email = :email")
    Optional<UserEntity> findByEmailWithTenants(String email);

    @EntityGraph(attributePaths = {"userTenants", "userTenants.tenant"})
    @Query("""
                SELECT u
                FROM UserEntity u
                JOIN u.userTenants ut
                WHERE u.id = :id
                  AND ut.tenant.id = :idTenant
                  AND ut.active = true
            """)
    UserEntity findByIdAndIdTenant(UUID id, UUID idTenant);
}