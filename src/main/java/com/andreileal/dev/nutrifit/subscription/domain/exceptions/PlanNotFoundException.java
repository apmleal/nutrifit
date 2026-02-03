package com.andreileal.dev.nutrifit.subscription.domain.exceptions;

import java.util.UUID;

public class PlanNotFoundException extends DomainException {

    public PlanNotFoundException(UUID id) {
        super("Plano não encotrado: " + id);
    }

    public PlanNotFoundException() {
        super("Plano não encontrado");
    }
}
