package com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.UUID;

@MappedSuperclass
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "idTenant", type = UUID.class))
@Filter(name = "tenantFilter", condition = "id_tenant = :idTenant")
@Getter
@Setter
public abstract class TenantBaseEntity {

    @Column(name = "id_tenant", nullable = false, updatable = false)
    protected UUID IdTenant;
}