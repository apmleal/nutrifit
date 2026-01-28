package com.andreileal.dev.nutrifit.subscription.infrastructure.conts;


public final class SecurityWhiteList {

    private SecurityWhiteList() {
    }

    public static final String[] PUBLIC_ENDPOINTS = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",

            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",

            // --route publicas
            "/auth/**",
            "/teste"
    };
}
