package com.adaneinstein.pedidos.dto.order;

import com.adaneinstein.pedidos.dto.client.ClientResponseDTO;
import com.adaneinstein.pedidos.model.Order;

import java.util.Date;
import java.util.Optional;

public record OrderResponseDTO(
        Long id,
        String protocol,
        Date createdAt,
        String name,
        Double price,
        Integer count,
        Double amount,
        Optional<ClientResponseDTO> client
) {

    public OrderResponseDTO(Order order) {
        this(order.getId(),
                order.getProtocol(),
                order.getCreatedAt(),
                order.getName(),
                order.getPrice(),
                order.getCount(),
                order.getAmount(),
                Optional.ofNullable(order.getClient()).stream().map(ClientResponseDTO::new).findFirst());
    }
}
