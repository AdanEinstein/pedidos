package com.adaneinstein.pedidos.service.impl;

import com.adaneinstein.pedidos.model.Client;
import com.adaneinstein.pedidos.repository.ClientRepository;
import com.adaneinstein.pedidos.service.Service;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ClientServiceImpl implements Service<Client, Long> {
    private final ClientRepository clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    @Override
    public List<Client> getAll() {
        return this.clientRepository.findAll();
    }

    @Override
    public Optional<Client> getById(Long id) {
        return this.clientRepository.findById(id);
    }

    @Override
    public Client save(Client target) {
        return this.clientRepository.save(target);
    }

    @Override
    public boolean deleteById(Long id) {
        var clientOptional = this.clientRepository.findById(id);
        if(clientOptional.isEmpty()) return false;
        this.clientRepository.deleteById(id);
        return true;
    }
}
