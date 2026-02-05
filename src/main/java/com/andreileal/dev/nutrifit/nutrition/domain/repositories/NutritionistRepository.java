package com.andreileal.dev.nutrifit.nutrition.domain.repositories;

import com.andreileal.dev.nutrifit.nutrition.domain.models.Nutritionist;

public interface NutritionistRepository {
    Nutritionist save(Nutritionist tenant);
}
