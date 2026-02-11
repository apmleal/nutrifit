package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entities.EntityBase;
import com.andreileal.dev.nutrifit.subscription.domain.models.Role;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_user", schema = "subscription")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserEntity extends EntityBase {

    private String name;
    private String email;
    private String password;
    private boolean active;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserTenantEntity> userTenants = new ArrayList<>();

    public void addTenant(TenantEntity tenant, Role role) {
        UserTenantEntity tenantUserEntity = new UserTenantEntity(this, tenant, role, true);
        userTenants.add(tenantUserEntity);
    }
}