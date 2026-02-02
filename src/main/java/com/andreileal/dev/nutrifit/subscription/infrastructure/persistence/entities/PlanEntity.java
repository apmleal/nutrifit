package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import java.util.Set;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entity.EntityBase;
import com.andreileal.dev.nutrifit.subscription.domain.models.Feature;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_plan", schema = "subscription")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
public class PlanEntity extends EntityBase {

    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Feature> features;

}
