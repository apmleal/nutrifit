package com.andreileal.dev.nutrifit.subscription.domain.repositories;

import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;

public interface TenantRepository {
    Tenant save(Tenant tenant);
}
