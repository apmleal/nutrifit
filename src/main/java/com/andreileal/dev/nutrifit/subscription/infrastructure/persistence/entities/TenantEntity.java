package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entity.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_tenant", schema = "subscription")
public class TenantEntity extends EntityBase {
    private String name;
    private boolean active;

}
