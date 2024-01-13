package com.adaneinstein.pedidos.controller;

import com.adaneinstein.pedidos.constants.ResponseMessages;
import com.adaneinstein.pedidos.dto.Response;
import com.adaneinstein.pedidos.dto.client.ClientRequestDTO;
import com.adaneinstein.pedidos.dto.client.ClientResponseDTO;
import com.adaneinstein.pedidos.model.Client;
import com.adaneinstein.pedidos.service.Service;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private Service<Client, Long> clientService;

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Response<List<ClientResponseDTO>>> getAllClients(){
        var clients = this.clientService.getAll()
                .stream()
                .map(ClientResponseDTO::new)
                .toList();
        return ResponseEntity.ok(new Response<>(clients, ResponseMessages.SUCCESS.toString()));
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Response<ClientResponseDTO>> saveClient(@RequestBody @Valid ClientRequestDTO clientRequestDTO){
        var newClient = new Client(clientRequestDTO);
        var client = this.clientService.save(newClient);
        var response = new Response<>(new ClientResponseDTO(client), ResponseMessages.SUCCESS.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Response<Boolean>> deleteClient(@PathVariable("id") Long id){
        var hasDeleted = this.clientService.deleteById(id);
        return ResponseEntity.ok(new Response<>(hasDeleted, ResponseMessages.SUCCESS.toString()));
    }

}
