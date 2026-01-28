package com.andreileal.dev.nutrifit.subscription.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class TesteController {
    @GetMapping("/teste")
    public ResponseEntity<String> naoSeguro() {
        return ResponseEntity.ok("Autenticado");
    }

    @GetMapping("/api/teste-seguro")
    public ResponseEntity<String> seguro() {
        return ResponseEntity.ok("Autenticado");
    }
}
