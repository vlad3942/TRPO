package ru.ssau.delivery.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dishes_in_order", uniqueConstraints = {
        @UniqueConstraint(columnNames = "order_id"),
        @UniqueConstraint(columnNames = "dish_id")
})
public class DishesInOrder {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "dish_id", nullable = false)
    private Dish dish;

    private Integer amount;
}
