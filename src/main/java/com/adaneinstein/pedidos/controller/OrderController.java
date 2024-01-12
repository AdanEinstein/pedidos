package com.adaneinstein.pedidos.controller;

import com.adaneinstein.pedidos.constants.ResponseMessages;
import com.adaneinstein.pedidos.dto.Response;
import com.adaneinstein.pedidos.dto.order.OrderRequestDTO;
import com.adaneinstein.pedidos.dto.order.OrderResponseDTO;
import com.adaneinstein.pedidos.model.Client;
import com.adaneinstein.pedidos.model.Order;
import com.adaneinstein.pedidos.service.Service;
import com.adaneinstein.pedidos.service.impl.ClientServiceImpl;
import com.adaneinstein.pedidos.service.impl.OrderServiceImpl;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    private final ClientServiceImpl clientService;

    public OrderController(OrderServiceImpl orderService, ClientServiceImpl clientService) {
        this.orderService = orderService;
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<Response<List<OrderResponseDTO>>> getAllOrders(){
        var orders = this.orderService.getAll()
                .stream()
                .map(OrderResponseDTO::new)
                .toList();
        return ResponseEntity.ok(new Response<>(orders, ResponseMessages.SUCCESS.toString()));
    }

    @GetMapping("/search")
    public ResponseEntity<Response<List<OrderResponseDTO>>> searchOrders(@RequestParam(required = false) String protocol,
                                                                         @RequestParam(required = false) Date createdAt){
        var result = this.orderService
                .serachByProtocolOrCreatedAt(protocol, createdAt)
                .stream()
                .map(OrderResponseDTO::new)
                .toList();
        return ResponseEntity.ok(new Response<>(result, ResponseMessages.SUCCESS.toString()));
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT})
    public ResponseEntity<Response<OrderResponseDTO>> saveOrder(@RequestBody @Valid OrderRequestDTO orderRequestDTO){
        var newOrder = new Order(orderRequestDTO);
        var client = this.clientService.getById(orderRequestDTO.clientId());
        newOrder.setClient(client.orElseThrow(() -> new NoSuchElementException("Cliente não encontrado")));
        var order = this.orderService.save(newOrder);
        var response = new Response<>(new OrderResponseDTO(order), ResponseMessages.SUCCESS.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Boolean>> deleteOrder(@PathVariable("id") Long id){
        var hasDeleted = this.orderService.deleteById(id);
        return ResponseEntity.ok(new Response<>(hasDeleted, ResponseMessages.SUCCESS.toString()));
    }

}
