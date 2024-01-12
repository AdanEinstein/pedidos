package com.adaneinstein.pedidos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record Response<T>(
        T data,
        @NotNull @NotBlank String message
) {
}
