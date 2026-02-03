package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entity.EntityBase;
import jakarta.persistence.*;
import lombok.*;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_tenant", nullable = false)
    private TenantEntity tenant;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_plan", nullable = false)
    private PlanEntity plan;

}