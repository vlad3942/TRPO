package ru.ssau.delivery.pojo;

import lombok.Value;

@Value
public class CreateDishRequest {
    String name;
    String description;
    Integer amount;
    Double price;
}
