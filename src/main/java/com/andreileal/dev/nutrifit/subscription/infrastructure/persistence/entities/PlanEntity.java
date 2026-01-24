package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entity.EntityBase;
import jakarta.persistence.*;

import java.util.Set;

@Entity
@Table(name = "tb_plan", schema = "subscription")
public class PlanEntity extends EntityBase {

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Feature> features;

}

