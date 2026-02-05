package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.impl;

import org.springframework.stereotype.Repository;

import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.TenantRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.TenantMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaPlanRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaTenantRepository;

@Repository
public class TenantRepositoryJpaImpl implements TenantRepository {

    private final JpaTenantRepository tenantRepositoryJpa;
    private final JpaPlanRepository planRepositoryJpa;

    public TenantRepositoryJpaImpl(JpaTenantRepository tenantRepositoryJpa, JpaPlanRepository planRepositoryJpa) {
        this.tenantRepositoryJpa = tenantRepositoryJpa;
        this.planRepositoryJpa = planRepositoryJpa;
    }

    @Override
    public Tenant save(Tenant tenant) {
        var entity = TenantMapper.toEntity(tenant);
        entity.setPlan(planRepositoryJpa.getReferenceById(tenant.getIdPlan()));
        var entitySaved = tenantRepositoryJpa.save(entity);
        return TenantMapper.toDomain(entitySaved);
    }
}
