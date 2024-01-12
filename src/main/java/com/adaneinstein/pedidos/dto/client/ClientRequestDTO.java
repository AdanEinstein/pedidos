package com.adaneinstein.pedidos.dto.client;

import jakarta.validation.constraints.NotBlank;

import java.util.Optional;

public record ClientRequestDTO(
    Optional<Long> id,
    @NotBlank
    String name
) {}
