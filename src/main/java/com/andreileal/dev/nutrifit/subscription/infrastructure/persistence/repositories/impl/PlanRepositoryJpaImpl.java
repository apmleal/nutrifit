package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.impl;

import com.andreileal.dev.nutrifit.subscription.domain.models.Plan;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.PlanRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.PlanMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaPlanRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class PlanRepositoryJpaImpl implements PlanRepository {

    private final JpaPlanRepository planRepositoryJpa;

    public PlanRepositoryJpaImpl(JpaPlanRepository planRepositoryJpa) {
        this.planRepositoryJpa = planRepositoryJpa;
    }

    @Override
    public Optional<Plan> findPlanById(UUID id) {
        return planRepositoryJpa.findById(id)
                .map(PlanMapper::toDomain);
    }

}
