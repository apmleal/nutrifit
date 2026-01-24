package com.andreileal.dev.nutrifit.subscription.domain.services;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;

public interface PasswordHasher {
    SenhaHasheada hash(SenhaPlana senhaPlana);

    boolean matches(SenhaPlana senhaPlana, SenhaHasheada senhaHasheada);
}
