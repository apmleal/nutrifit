package com.andreileal.dev.nutrifit.subscription.application.usecases;

import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.commands.LoginCommand;
import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.results.LoginResult;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.CredenciaisInvalidasException;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.UsuarioNaoEncontradoException;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.TokenGenerator;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    private final UserRepository userRepository;
    private final TokenGenerator jwtProvider;
    private final PasswordHasher passwordHasher;

    public LoginUseCase(UserRepository userRepository, TokenGenerator jwtProvider, PasswordHasher passwordHasher) {

        this.userRepository = userRepository;
        this.jwtProvider = jwtProvider;
        this.passwordHasher = passwordHasher;
    }

    public LoginResult execute(LoginCommand command) {

        Email emailVO = new Email(command.email());
        SenhaPlana senhaPlana = new SenhaPlana(command.password());

        var user = userRepository.findUserByEmail(emailVO.valor())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(emailVO.valor()));

        if (!user.isPasswordValid(senhaPlana, passwordHasher)) {
            throw new CredenciaisInvalidasException();
        }

        var token = jwtProvider.gerar(user.getEmail().valor(), user.getIdTenant(), user.getRole().name());

        return new LoginResult(
                token.toString(),
                user.getId(),
                user.getEmail().valor(),
                user.getNome().valor());
    }
}
