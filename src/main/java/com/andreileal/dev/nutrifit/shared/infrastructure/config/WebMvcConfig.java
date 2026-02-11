package com.andreileal.dev.nutrifit.shared.infrastructure.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.andreileal.dev.nutrifit.shared.infrastructure.persistence.filters.TenantFilterInterceptor;

/**
 * Configuracao do Spring MVC para registrar interceptors customizados.
 * 
 * O TenantFilterInterceptor e registrado para interceptar todas as requisicoes
 * que acessam endpoints protegidos (/api/**), garantindo que o filtro Hibernate
 * de multi-tenancy seja habilitado com o tenant correto.
 */
@Configuration(proxyBeanMethods = false)
public class WebMvcConfig implements WebMvcConfigurer {

    private final TenantFilterInterceptor tenantFilterInterceptor;

    public WebMvcConfig(TenantFilterInterceptor tenantFilterInterceptor) {
        this.tenantFilterInterceptor = tenantFilterInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(tenantFilterInterceptor)
                .addPathPatterns("/api/**")
                .order(1);
    }
}
