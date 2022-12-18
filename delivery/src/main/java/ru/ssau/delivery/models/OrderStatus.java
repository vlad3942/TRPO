package ru.ssau.delivery.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "order_status")
public class OrderStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EOrderStatus name;

    public OrderStatus() {}

    public OrderStatus(EOrderStatus name) {
        this.name = name;
    }
}
