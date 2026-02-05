package com.andreileal.dev.nutrifit.nutrition.application.dto.commands;

public record CreateNutrtitionistCommand(
        String crn,
        String specialty) {
    public CreateNutrtitionistCommand {
        if (crn == null || crn.isBlank()) {
            throw new IllegalArgumentException("CRN nao pode ser vazio");
        }
        if (specialty == null || specialty.isBlank()) {
            throw new IllegalArgumentException("Especialidade nao pode ser vazio");
        }
    }
}