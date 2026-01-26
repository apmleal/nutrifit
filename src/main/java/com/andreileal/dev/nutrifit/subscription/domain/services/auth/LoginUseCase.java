package com.andreileal.dev.nutrifit.subscription.domain.services.auth;

public interface LoginUseCase {

    public String execute(String email, String password);
}
