package com.andreileal.dev.nutrifit.subscription.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.andreileal.dev.nutrifit.subscription.application.usecases.LoginUseCase;
import com.andreileal.dev.nutrifit.subscription.application.usecases.dto.LoginCommand;
import com.andreileal.dev.nutrifit.subscription.presentation.dtos.requests.RequestAutenticacaoDto;
import com.andreileal.dev.nutrifit.subscription.presentation.dtos.responses.LoginResponseDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> signin(@Valid @RequestBody RequestAutenticacaoDto request) {

        var command = new LoginCommand(request.email(), request.senha());
        var result = loginUseCase.execute(command);

        return ResponseEntity.ok(LoginResponseDto.fromLoginResult(result));
    }
}
