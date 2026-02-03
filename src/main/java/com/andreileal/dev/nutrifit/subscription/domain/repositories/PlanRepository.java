package com.andreileal.dev.nutrifit.subscription.domain.repositories;

import com.andreileal.dev.nutrifit.subscription.domain.models.Plan;

import java.util.Optional;
import java.util.UUID;

public interface PlanRepository {
    Optional<Plan> findPlanById(UUID id);
}
