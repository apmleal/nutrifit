package com.andreileal.dev.nutrifit.subscription.application.usecases;

import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.LoginUseCase;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.TokenGenerator;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCaseImpl implements LoginUseCase {

    private final UserRepository userRepository;
    private final TokenGenerator jwtProvider;
    private final PasswordHasher passwordHasher;


    public LoginUseCaseImpl(UserRepository userRepository, TokenGenerator jwtProvider, PasswordHasher passwordHasher) {

        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public String execute(String email, String password) {
        var user = userRepository.findUserByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Credenciais invalidas"));

        if (!user.isPassworValid(new SenhaPlana(password), passwordHasher)) {
            throw new IllegalArgumentException("Credenciais invalidas");
        }

        var token = jwtProvider.gerar(user.getEmail());

        return token.toString();
    }
}
