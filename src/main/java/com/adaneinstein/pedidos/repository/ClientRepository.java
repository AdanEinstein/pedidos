package com.adaneinstein.pedidos.repository;

import com.adaneinstein.pedidos.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
