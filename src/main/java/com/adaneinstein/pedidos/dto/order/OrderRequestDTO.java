package com.adaneinstein.pedidos.dto.order;

import jakarta.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
import java.util.Optional;

public record OrderRequestDTO(
        Optional<Long> id,
    @NotBlank @NotNull String protocol,
    @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> createdAt,
    @NotBlank String name,
    @NotNull @PositiveOrZero Double price,
    Optional<Integer> count,
    @NotNull Long clientId
) {}
