package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entity.EntityBase;
import com.andreileal.dev.nutrifit.subscription.domain.models.Feature;
import jakarta.persistence.*;
import lombok.*;

import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_plan", schema = "subscription")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlanEntity extends EntityBase {

    private String name;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(
            name = "tb_plan_features",
            schema = "subscription",
            joinColumns = @JoinColumn(name = "id_plan")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "feature")
    private Set<Feature> features;

    public PlanEntity(UUID id) {
        super(id);
    }

}
