package com.adaneinstein.pedidos.repository;

import com.adaneinstein.pedidos.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByProtocolAndCreatedAt(String protocol, Date createdAt);
    List<Order> findByCreatedAt(Date createdAt);
    List<Order> findByProtocol(String protocol);
}
