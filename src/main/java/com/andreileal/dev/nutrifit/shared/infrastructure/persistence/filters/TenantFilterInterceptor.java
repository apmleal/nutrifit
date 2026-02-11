package com.andreileal.dev.nutrifit.shared.infrastructure.persistence.filters;

import java.util.UUID;

import org.hibernate.Session;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.andreileal.dev.nutrifit.subscription.infrastructure.config.context.TenantContext;

import jakarta.persistence.EntityManager;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

/**
 * Interceptor que habilita o filtro Hibernate de multi-tenancy POR REQUEST,
 * garantindo isolamento de dados entre tenants.
 * 
 * Este interceptor e executado APOS a autenticacao (AuthTokenFilter),
 * quando o TenantContext ja foi configurado com o ID do tenant correto.
 */
@Component
@Slf4j
public class TenantFilterInterceptor implements HandlerInterceptor {

    private final EntityManager entityManager;

    public TenantFilterInterceptor(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        UUID tenantId = TenantContext.getTenantId();
        
        // So habilita o filtro se houver um tenant no contexto
        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);
            
            // Habilita o filtro Hibernate com o ID do tenant atual
            session.enableFilter("tenantFilter")
                    .setParameter("idTenant", tenantId);
            
            log.debug("Hibernate tenantFilter habilitado para tenant: {}", tenantId);
        }
        
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
                                Object handler, Exception ex) {
        UUID tenantId = TenantContext.getTenantId();
        
        if (tenantId != null) {
            Session session = entityManager.unwrap(Session.class);
            
            // Desabilita o filtro apos a requisicao
            session.disableFilter("tenantFilter");
            
            log.debug("Hibernate tenantFilter desabilitado para tenant: {}", tenantId);
        }
    }
}
