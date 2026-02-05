package com.andreileal.dev.nutrifit.nutrition.infrastructure.persistence.repositories;

import com.andreileal.dev.nutrifit.nutrition.infrastructure.persistence.entities.NutritionistEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaNutritionistRepository extends JpaRepository<NutritionistEntity, UUID> {
}
