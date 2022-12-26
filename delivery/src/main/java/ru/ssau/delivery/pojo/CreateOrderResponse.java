package ru.ssau.delivery.pojo;

import lombok.Data;

@Data
public class CreateOrderResponse {
    private Long id;
    private String message;

    public CreateOrderResponse(Long id, String message) {
        this.id = id;
        this.message = message;
    }
}
