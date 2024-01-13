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
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.text.DateFormat;
import java.text.ParseException;
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

    @GetMapping(produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Response<List<OrderResponseDTO>>> getAllOrders(){
        var orders = this.orderService.getAll()
                .stream()
                .map(OrderResponseDTO::new)
                .toList();
        return ResponseEntity.ok(new Response<>(orders, ResponseMessages.SUCCESS.toString()));
    }

    @GetMapping(path = "/search", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Response<List<OrderResponseDTO>>> searchOrders(@RequestParam(required = false) String protocol,
                                                                         @RequestParam(required = false)
                                                                         @DateTimeFormat(pattern = "yyyy-MM-dd") Date createdAt) {
        var result = this.orderService
                .serachByProtocolOrCreatedAt(protocol, createdAt)
                .stream()
                .map(OrderResponseDTO::new)
                .toList();
        return ResponseEntity.ok(new Response<>(result, ResponseMessages.SUCCESS.toString()));
    }

    @RequestMapping(method = {RequestMethod.POST, RequestMethod.PUT}, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Response<List<OrderResponseDTO>>> saveOrder(@RequestBody @Valid List<OrderRequestDTO> orderRequestDTOList){
        if (orderRequestDTOList.size() > 10) throw new NoSuchElementException("Quantidade de pedidos excedida");
        var ordersSaved = orderRequestDTOList
                .stream()
                .map(orderRequestDTO -> {
            var newOrder = new Order(orderRequestDTO);
            var client = this.clientService.getById(orderRequestDTO.clientId());
            newOrder.setClient(client.orElseThrow(() -> new NoSuchElementException("Cliente não encontrado")));
            return newOrder;
        })
                .map(this.orderService::save)
                .map(OrderResponseDTO::new)
                .toList();
        var response = new Response<>(ordersSaved, ResponseMessages.SUCCESS.toString());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping(path = "/{id}", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
    public ResponseEntity<Response<Boolean>> deleteOrder(@PathVariable("id") Long id){
        var hasDeleted = this.orderService.deleteById(id);
        return ResponseEntity.ok(new Response<>(hasDeleted, ResponseMessages.SUCCESS.toString()));
    }

}
