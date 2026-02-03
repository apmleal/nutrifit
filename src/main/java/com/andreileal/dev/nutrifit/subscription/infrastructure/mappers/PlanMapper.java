package com.andreileal.dev.nutrifit.subscription.infrastructure.mappers;

import com.andreileal.dev.nutrifit.subscription.domain.models.Plan;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.PlanEntity;

public class PlanMapper {

    private PlanMapper() {

    }

    public static Plan toDomain(PlanEntity entity) {
        if (entity == null) {
            return null;
        }
        Nome nome = new Nome(entity.getName());
        return Plan.reconstituir(entity.getId(), nome);
    }

    public static PlanEntity toEntity(Plan domain) {
        if (domain == null) {
            return null;
        }
        PlanEntity entity = new PlanEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getNome().valor());
        return entity;
    }
}
