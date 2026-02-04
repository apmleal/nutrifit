package com.andreileal.dev.nutrifit.subscription.presentation.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasRole('ADMINISTRATOR')")
    @GetMapping("/api/teste-seguro/admin")
    public ResponseEntity<String> seguroAdmin() {
        return ResponseEntity.ok("Autenticado Admin");
    }

    @PreAuthorize("hasRole('PATIENT')")
    @GetMapping("/api/teste-seguro/paciente")
    public ResponseEntity<String> seguroPaciente() {
        return ResponseEntity.ok("Autenticado Paciente");
    }

    @PreAuthorize("hasAnyRole('PATIENT', 'ADMINISTRATOR')")
    @GetMapping("/api/teste-seguro/admin-paciente")
    public ResponseEntity<String> seguroAdmPaciente() {
        return ResponseEntity.ok("Autenticado Adm Paciente");
    }
}
