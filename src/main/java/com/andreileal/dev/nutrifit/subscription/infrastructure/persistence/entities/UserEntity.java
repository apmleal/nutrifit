package com.andreileal.dev.nutrifit.subscription.infrastructure.persistence.entities;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.entity.EntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_user", schema = "subscription")
public class UserEntity extends EntityBase {

    private String email;
    private String password;

    @ManyToOne
    private TenantEntity tenant;


}