package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entities.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "tb_tenant", schema = "subscription")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class TenantEntity extends EntityBase {
    private String name;
    private boolean active;

    public TenantEntity(UUID id, String name) {
        super(id);
        this.name = name;
    }

}
