package com.andreileal.dev.nutrifit.nutrition.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entities.TenantBaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "tb_nutritionist", schema = "nutrition")
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NutritionistEntity extends TenantBaseEntity {
    public String crn;
    public String specialty;
    public String phone;
}
