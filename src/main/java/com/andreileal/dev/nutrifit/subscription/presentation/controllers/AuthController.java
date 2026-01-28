package com.andreileal.dev.nutrifit.subscription.presentation.controllers;

import com.andreileal.dev.nutrifit.subscription.domain.services.auth.LoginUseCase;
import com.andreileal.dev.nutrifit.subscription.presentation.dtos.RequestAutenticacaoDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;

    public AuthController(LoginUseCase loginUseCase) {
        this.loginUseCase = loginUseCase;
    }

    @PostMapping("/signin")
    public ResponseEntity<String> signin(@RequestBody RequestAutenticacaoDto request) {

        var token = loginUseCase.execute(request.email(), request.senha());

        return ResponseEntity.ok(token);
    }
}
