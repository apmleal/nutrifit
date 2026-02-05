package com.andreileal.dev.nutrifit.subscription.presentation.controllers;

import com.andreileal.dev.nutrifit.subscription.application.dto.commands.CreateUserCommand;
import com.andreileal.dev.nutrifit.subscription.application.dto.commands.LoginCommand;
import com.andreileal.dev.nutrifit.subscription.application.usecases.CreateUserUseCase;
import com.andreileal.dev.nutrifit.subscription.application.usecases.LoginUseCase;
import com.andreileal.dev.nutrifit.subscription.presentation.dtos.requests.RequestAutenticacaoDto;
import com.andreileal.dev.nutrifit.subscription.presentation.dtos.requests.RequestCreateUserDto;
import com.andreileal.dev.nutrifit.subscription.presentation.dtos.responses.CreateUserResponseDto;
import com.andreileal.dev.nutrifit.subscription.presentation.dtos.responses.LoginResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final CreateUserUseCase createUserUseCase;

    public AuthController(LoginUseCase loginUseCase, CreateUserUseCase createUserUseCase) {
        this.loginUseCase = loginUseCase;
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> signin(@Valid @RequestBody RequestAutenticacaoDto request) {

        var command = new LoginCommand(request.email(), request.senha());
        var result = loginUseCase.execute(command);

        return ResponseEntity.ok(LoginResponseDto.fromLoginResult(result));
    }

    @PostMapping("/signup")
    public ResponseEntity<CreateUserResponseDto> signup(@Valid @RequestBody RequestCreateUserDto request) {

        var command = new CreateUserCommand(request.email(), request.senha(), request.name(), request.idPlan());
        var result = createUserUseCase.execute(command);

        return ResponseEntity.ok(CreateUserResponseDto.fromCreateUserResult(result));
    }
}
