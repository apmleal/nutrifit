package com.andreileal.dev.nutrifit.nutrition.infrastructure.persistence.repositories.impl;

import com.andreileal.dev.nutrifit.nutrition.domain.models.Nutritionist;
import com.andreileal.dev.nutrifit.nutrition.domain.repositories.NutritionistRepository;
import org.springframework.stereotype.Repository;

@Repository
public class NutritionistRepositoryJpaImpl implements NutritionistRepository {

    @Override
    public Nutritionist save(Nutritionist tenant) {
        return null;
    }
    
}
