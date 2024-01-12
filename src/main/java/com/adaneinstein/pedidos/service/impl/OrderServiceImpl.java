package com.adaneinstein.pedidos.service.impl;

import com.adaneinstein.pedidos.model.Order;
import com.adaneinstein.pedidos.repository.OrderRepository;
import com.adaneinstein.pedidos.service.Service;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class OrderServiceImpl implements Service<Order, Long> {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public List<Order> getAll() {
        return this.orderRepository.findAll();
    }

    @Override
    public Optional<Order> getById(Long id) {
        return this.orderRepository.findById(id);
    }

    public List<Order> serachByProtocolOrCreatedAt(String protocol, Date createdAt) {
        var protocolOptional = Optional.ofNullable(protocol);
        var createdOptional = Optional.ofNullable(createdAt);
        if (protocolOptional.isPresent() && createdOptional.isPresent())
            return this.orderRepository.findByProtocolAndCreatedAt(protocolOptional.get(), createdOptional.get());
        if (protocolOptional.isPresent())
            return this.orderRepository.findByProtocol(protocolOptional.get());
        if (createdOptional.isPresent())
            return this.orderRepository.findByCreatedAt(createdOptional.get());
        return new ArrayList<>();
    }

    @Override
    public Order save(Order target) {
        return this.orderRepository.save(target);
    }

    @Override
    public boolean deleteById(Long id) {
        var orderOptional = this.orderRepository.findById(id);
        if (orderOptional.isEmpty()) return false;
        this.orderRepository.deleteById(id);
        return true;
    }
}
