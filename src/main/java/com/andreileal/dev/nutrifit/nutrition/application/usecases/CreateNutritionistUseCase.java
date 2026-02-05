package com.andreileal.dev.nutrifit.nutrition.application.usecases;

import com.andreileal.dev.nutrifit.nutrition.application.dto.commands.CreateNutrtitionistCommand;
import com.andreileal.dev.nutrifit.nutrition.domain.repositories.NutritionistRepository;
import com.andreileal.dev.nutrifit.subscription.application.usecases.CreateUserUseCase;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateNutritionistUseCase {

    private final NutritionistRepository nutritionistRepository;
    private final CreateUserUseCase createUserUseCase;

    public CreateNutritionistUseCase(NutritionistRepository nutritionistRepository, CreateUserUseCase createUserUseCase) {
        this.nutritionistRepository = nutritionistRepository;
        this.createUserUseCase = createUserUseCase;
    }

    @Transactional
    public void execute(CreateNutrtitionistCommand command) {

        
    }
}
