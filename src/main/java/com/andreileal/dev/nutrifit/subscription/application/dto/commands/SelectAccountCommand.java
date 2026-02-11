package com.andreileal.dev.nutrifit.subscription.application.dto.commands;

import java.util.UUID;

public record SelectAccountCommand(
        UUID user,
        UUID account) {
    public SelectAccountCommand {
        if (user == null) {
            throw new IllegalArgumentException("user not found");
        }
        if (account == null) {
            throw new IllegalArgumentException("account not found");
        }
    }
}
