package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories;

import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaTenantRepository extends JpaRepository<TenantEntity, UUID> {

}