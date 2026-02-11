package com.andreileal.dev.nutrifit.subscription.presentation.dtos.requests;

import java.util.UUID;

public record RequestSelectAccountDto(
        UUID usuario,
        UUID account) {
}
