package ru.ssau.delivery.models;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "dish")
public class Dish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    private String name;
    private String description;
    private Integer amount;
    private Double price;
}
