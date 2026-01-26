package com.andreileal.dev.nutrifit.subscription.infrastructure.auth;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaHasheada;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class BcryptPasswordHasher implements PasswordHasher {

    private final PasswordEncoder passwordEncoder;

    public BcryptPasswordHasher(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public SenhaHasheada hash(SenhaPlana senhaPlana) {
        String hash = passwordEncoder.encode(senhaPlana.plena());
        return new SenhaHasheada(hash);
    }

    @Override
    public boolean matches(SenhaPlana senhaPlana, SenhaHasheada senhaHasheada) {
        return passwordEncoder.matches(senhaPlana.plena(), senhaHasheada.hash());
    }
}
