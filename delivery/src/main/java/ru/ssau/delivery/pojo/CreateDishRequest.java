package ru.ssau.delivery.pojo;

import lombok.Data;

@Data
public class CreateDishRequest {
    private String name;
    private String description;
    private Integer amount;
    private Double price;
}
