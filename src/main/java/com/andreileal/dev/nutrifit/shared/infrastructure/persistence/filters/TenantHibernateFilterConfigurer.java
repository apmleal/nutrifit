package com.andreileal.dev.nutrifit.shared.infrastructure.persistence.filters;

import com.andreileal.dev.nutrifit.subscription.infrastructure.config.context.TenantContext;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
public class TenantHibernateFilterConfigurer {

    private final EntityManager entityManager;

    public TenantHibernateFilterConfigurer(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @PostConstruct
    public void configure() {
        Session session = entityManager.unwrap(Session.class);

        session.enableFilter("tenantFilter")
                .setParameter("idTenant", TenantContext.getTenantId());
    }
}