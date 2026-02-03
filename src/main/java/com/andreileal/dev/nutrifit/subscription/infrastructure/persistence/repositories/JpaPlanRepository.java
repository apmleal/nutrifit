package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories;

import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.PlanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPlanRepository extends JpaRepository<PlanEntity, UUID> {

}