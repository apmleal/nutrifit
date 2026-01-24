package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entity.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_user", schema = "subscription")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserEntity extends EntityBase {

    private String name;
    private String email;
    private String password;

    @ManyToOne
    private TenantEntity tenant;


}