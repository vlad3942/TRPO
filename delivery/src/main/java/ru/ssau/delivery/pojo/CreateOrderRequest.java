package ru.ssau.delivery.pojo;

import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private Long restId;
    private String deliveryAddress;
    private String comment;
    private List<DishInfo> dishes;

    @Data
    public static class DishInfo {
        private Long id;
        private Integer amount;
    }
}
