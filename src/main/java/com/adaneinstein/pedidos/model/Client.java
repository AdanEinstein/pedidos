package com.adaneinstein.pedidos.model;

import com.adaneinstein.pedidos.dto.client.ClientRequestDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "clientes")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String name;

    public Client(ClientRequestDTO clientRequestDTO) {
        clientRequestDTO.id().ifPresent(this::setId);
        this.name = clientRequestDTO.name();
    }
}
