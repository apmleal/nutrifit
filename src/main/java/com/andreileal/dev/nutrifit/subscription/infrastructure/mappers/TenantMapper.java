package com.andreileal.dev.nutrifit.subscription.infrastructure.mappers;

import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities.TenantEntity;

public class TenantMapper {

    private TenantMapper() {

    }

    public static Tenant toDomain(TenantEntity entity) {
        if (entity == null) {
            return null;
        }
        Nome nome = new Nome(entity.getName());
        return Tenant.reconstituir(entity.getId(), nome, entity.isActive());
    }

    public static TenantEntity toEntity(Tenant domain) {
        if (domain == null) {
            return null;
        }
        TenantEntity entity = new TenantEntity();
        entity.setId(domain.getId());
        entity.setName(domain.getNome().valor());
        entity.setActive(domain.isActive());
        return entity;
    }
}
