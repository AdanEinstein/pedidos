package com.adaneinstein.pedidos.model;

import com.adaneinstein.pedidos.dto.order.OrderRequestDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.Optional;

@Entity(name = "pedidos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = "protocol") })
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    private String protocol;
    @Column
    @Temporal(TemporalType.DATE)
    private Date createdAt = new Date();
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Double price;
    @Column
    private Integer count;
    @Column
    private Double amount;
    @ManyToOne(fetch = FetchType.EAGER)
    private Client client;

    public Order(OrderRequestDTO orderRequestDTO) {
        orderRequestDTO.id().ifPresent(this::setId);
        orderRequestDTO.createdAt().ifPresent(this::setCreatedAt);
        this.setCount(orderRequestDTO.count().orElse(1));
        this.protocol = orderRequestDTO.protocol();
        this.name = orderRequestDTO.name();
        this.price = orderRequestDTO.price();
        this.amount = this.calculatePrice();
    }

    private Double calculatePrice(){
        if (this.getCount() > 10) return this.getPrice() * this.getCount() * (1 - 0.1);
        if (this.getCount() > 5) return this.getPrice() * this.getCount() * (1 - 0.05);
        return this.getPrice() * this.getCount();
    }
}
