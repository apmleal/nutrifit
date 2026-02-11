package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entities.EntityBase;
import com.andreileal.dev.nutrifit.subscription.domain.models.Role;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tb_user_tenant", schema = "subscription")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserTenantEntity extends EntityBase {

    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "id_tenant", nullable = false)
    private TenantEntity tenant;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean active;

}