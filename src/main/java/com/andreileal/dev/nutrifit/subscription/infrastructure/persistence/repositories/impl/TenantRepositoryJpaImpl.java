package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.impl;

import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.TenantRepository;
import com.andreileal.dev.nutrifit.subscription.infrastructure.mappers.TenantMapper;
import com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.repositories.JpaTenantRepository;
import org.springframework.stereotype.Repository;

@Repository
public class TenantRepositoryJpaImpl implements TenantRepository {

    private final JpaTenantRepository tenantRepositoryJpa;

    public TenantRepositoryJpaImpl(JpaTenantRepository tenantRepositoryJpa) {
        this.tenantRepositoryJpa = tenantRepositoryJpa;
    }


    @Override
    public Tenant save(Tenant tenant) {
        var entity = TenantMapper.toEntity(tenant);
        var entitySaved = tenantRepositoryJpa.save(entity);
        return TenantMapper.toDomain(entitySaved);
    }
}
