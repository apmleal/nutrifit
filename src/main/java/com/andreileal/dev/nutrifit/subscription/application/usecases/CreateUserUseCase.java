package com.andreileal.dev.nutrifit.subscription.application.usecases;

import org.springframework.stereotype.Service;

import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.commands.CreateUserCommand;
import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.results.CreateUserResult;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.UsuarioJaCadastradoException;
import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PasswordHasher passwordHasher;

    public CreateUserUseCase(UserRepository userRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.passwordHasher = passwordHasher;
    }

    public CreateUserResult execute(CreateUserCommand command) {
        Email emailVO = new Email(command.email());
        SenhaPlana senhaPlana = new SenhaPlana(command.password());
        Nome name = new Nome(command.name());

        var user = userRepository.findUserByEmail(emailVO.valor());

        if (user.isPresent()) {
            throw new UsuarioJaCadastradoException(emailVO.valor());
        }

        User newUser = User.criar(emailVO, name, senhaPlana, passwordHasher);

        newUser = userRepository.save(newUser);

        return new CreateUserResult(newUser.getId(), emailVO.valor(), name.valor());

    }
}
