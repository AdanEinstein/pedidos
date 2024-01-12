package com.adaneinstein.pedidos.dto.client;

import com.adaneinstein.pedidos.model.Client;

public record ClientResponseDTO(
    Long id,
    String name
) {
    public ClientResponseDTO(Client client){
        this(client.getId(), client.getName());
    }
}
