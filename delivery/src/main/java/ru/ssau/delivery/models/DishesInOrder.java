package ru.ssau.delivery.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dishes_in_order")
public class DishesInOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    private Integer amount;
}
