package com.andreileal.dev.nutrifit.subscription.application.usecases;

import com.andreileal.dev.nutrifit.subscription.application.dto.commands.LoginCommand;
import com.andreileal.dev.nutrifit.subscription.application.dto.results.LoginResult;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.CredenciaisInvalidasException;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.UsuarioNaoEncontradoException;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public LoginUseCase(UserRepository userRepository, PasswordHasher passwordHasher) {

        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public LoginResult execute(LoginCommand command) {

        Email emailVO = new Email(command.email());
        SenhaPlana senhaPlana = new SenhaPlana(command.password());

        var user = userRepository.findByEmailWithTenants(emailVO.valor())
                .orElseThrow(() -> new UsuarioNaoEncontradoException(emailVO.valor()));

        if (!user.isPasswordValid(senhaPlana, passwordHasher)) {
            throw new CredenciaisInvalidasException();
        }

//        var token = jwtProvider.gerar(user.getEmail().valor(), user.getIdTenant(), user.getRole().name());

        return new LoginResult(
                user.getId(),
                user.getEmail().valor(),
                user.getNome().valor(),
                user.getTenants());
    }
}
