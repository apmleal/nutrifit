package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entities.EntityBase;
import com.andreileal.dev.nutrifit.subscription.domain.models.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import java.util.UUID;

@Entity
@Table(name = "tb_user", schema = "subscription")
@FilterDef(name = "tenantFilter", parameters = @ParamDef(name = "idTenant", type = UUID.class))
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

    @Enumerated(EnumType.STRING)
    private Role role;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private TenantEntity tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanEntity plan;


}