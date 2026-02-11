package com.andreileal.dev.nutrifit.subscription.application.usecases;

import com.andreileal.dev.nutrifit.subscription.application.dto.commands.SelectAccountCommand;
import com.andreileal.dev.nutrifit.subscription.application.dto.results.SelectAccountResult;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.TokenGenerator;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
public class SelectAccountUseCase {

    private final UserRepository userRepository;
    private final TokenGenerator jwtProvider;

    public SelectAccountUseCase(UserRepository userRepository, TokenGenerator jwtProvider) {

        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
    }

    public SelectAccountResult execute(SelectAccountCommand command) {

        var user = userRepository.findUserByIdAndIdTenant(command.user(), command.account());

        if (user == null) {
            throw new AccessDeniedException("User not found");
        }

        var token = jwtProvider.gerar(user.getEmail().valor(), command.account(), user.getRole().name());

        return new SelectAccountResult(
                user.getId(),
                user.getEmail().valor(),
                user.getNome().valor(),
                token.valor());
    }
}
