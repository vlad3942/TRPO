package ru.ssau.delivery.pojo;

import lombok.Data;
import ru.ssau.delivery.models.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {
    private Long id;
    private Long userId;
    private RestaurantInfo restaurant;
    private CourierInfo courier;
    private String status;
    private String deliveryAddress;
    private Double totalPrice;
    private String comment;
    private List<DishInfo> dishes = new ArrayList<>();

    @Data
    public static class RestaurantInfo {
        private long id;
        private String name;
        private String address;
    }

    @Data
    public static class CourierInfo {
        private long id;
        private String name;
    }

    public static List<OrderResponse> convert(List<Order> orders) {
        if (orders.isEmpty()) {
            return List.of();
        }
        return orders.stream().map(OrderResponse::convert).collect(Collectors.toList());
    }

    private static OrderResponse convert(Order order) {
        OrderResponse response = new OrderResponse();
        response.setId(order.getId());
        response.setComment(order.getComment());
        response.setDeliveryAddress(order.getDeliveryAddress());
        response.setTotalPrice(order.getTotalPrice());
        response.setUserId(order.getUser().getId());
        response.setStatus(order.getStatus().getName().toString());
        var restInfo = new RestaurantInfo();
        restInfo.id = order.getRestaurant().getId();
        restInfo.name = order.getRestaurant().getName();
        restInfo.address = order.getRestaurant().getAddress();
        response.setRestaurant(restInfo);
        if (order.getCourier() != null) {
            var courierInfo = new CourierInfo();
            courierInfo.id = order.getCourier().getId();
            courierInfo.name = order.getCourier().getName();
            response.setCourier(courierInfo);
        } else {
            response.setCourier(null);
        }
        return response;
    }
}
