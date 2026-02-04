package com.andreileal.dev.nutrifit.subscription.application.usecases;

import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.commands.CreateUserCommand;
import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.results.CreateUserResult;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.PlanNotFoundException;
import com.andreileal.dev.nutrifit.subscription.domain.exceptions.UsuarioJaCadastradoException;
import com.andreileal.dev.nutrifit.subscription.domain.models.Role;
import com.andreileal.dev.nutrifit.subscription.domain.models.Tenant;
import com.andreileal.dev.nutrifit.subscription.domain.models.User;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Email;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.Nome;
import com.andreileal.dev.nutrifit.subscription.domain.models.valueobjects.SenhaPlana;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.PlanRepository;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.TenantRepository;
import com.andreileal.dev.nutrifit.subscription.domain.repositories.UserRepository;
import com.andreileal.dev.nutrifit.subscription.domain.services.auth.PasswordHasher;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CreateUserUseCase {

    private final UserRepository userRepository;
    private final PlanRepository planRepository;
    private final TenantRepository tenantRepository;
    private final PasswordHasher passwordHasher;

    public CreateUserUseCase(UserRepository userRepository, PlanRepository planRepository, TenantRepository tenantRepository, PasswordHasher passwordHasher) {
        this.userRepository = userRepository;
        this.planRepository = planRepository;
        this.tenantRepository = tenantRepository;
        this.passwordHasher = passwordHasher;
    }

    @Transactional
    public CreateUserResult execute(CreateUserCommand command) {
        Email emailVO = new Email(command.email());
        SenhaPlana senhaPlana = new SenhaPlana(command.password());
        Nome name = new Nome(command.name());

        var user = userRepository.findUserByEmail(emailVO.valor());

        if (user.isPresent()) {
            throw new UsuarioJaCadastradoException(emailVO.valor());
        }

        var plan = planRepository.findPlanById(command.idPlan()).orElseThrow(() -> new PlanNotFoundException(command.idPlan()));
        var tenant = Tenant.criar(new Nome(emailVO.valor()));
        var newUser = User.criar(emailVO, name, senhaPlana, passwordHasher, tenant.getId(), Role.ADMINISTRATOR, true);

        tenant = tenantRepository.save(tenant);
        newUser = userRepository.createAccount(newUser, plan, tenant);

        return new CreateUserResult(newUser.getId(), emailVO.valor(), name.valor());

    }
}
