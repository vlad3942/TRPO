package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.delivery.models.*;
import ru.ssau.delivery.pojo.MessageResponse;
import ru.ssau.delivery.pojo.OrderResponse;
import ru.ssau.delivery.repository.CourierRepository;
import ru.ssau.delivery.repository.OrderRepository;
import ru.ssau.delivery.repository.OrderStatusRepository;
import ru.ssau.delivery.service.UserService;

import java.util.List;

@Slf4j
@RestController
@PreAuthorize("hasRole('COURIER')")
@RequestMapping("/api/courier")
@RequiredArgsConstructor
public class CourierController {

    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final CourierRepository courierRepository;

    @GetMapping("/restaurants/{restaurant_id}/orders")
    public ResponseEntity<?> getOrdersWithoutCourier(
            @PathVariable("restaurant_id") Long restaurantId
    ) {
        OrderStatus status = orderStatusRepository.findByName(EOrderStatus.WAITING_COURIER).orElseThrow();
        List<Order> orders = orderRepository.findAllByRestaurantIdAndStatus(restaurantId, status);
        List<OrderResponse> result = OrderResponse.convert(orders);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/take-order/{order_id}")
    public ResponseEntity<?> takeOrder(Authentication authentication, @PathVariable("order_id") Long orderId) {
        User user = userService.obtainUser(authentication);
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (order.getCourier() != null) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: courier is already assigned to order with id - " + orderId));
        }
        Courier courier = courierRepository.findByUser(user).orElseThrow();
        order.setCourier(courier);
        order.setStatus(orderStatusRepository.findByName(EOrderStatus.IN_PROGRESS).orElseThrow());
        orderRepository.saveAndFlush(order);
        return ResponseEntity.ok(new MessageResponse("Order was successfully assigned"));
    }

    @GetMapping("/close-order/{order_id}")
    public ResponseEntity<?> closeOrder(Authentication authentication, @PathVariable("order_id") Long orderId) {
        User user = userService.obtainUser(authentication);
        Order order = orderRepository.findById(orderId).orElseThrow();
        Courier courier = courierRepository.findByUser(user).orElseThrow();
        if (!order.getCourier().equals(courier)) {
            return ResponseEntity.badRequest()
                    .body(new MessageResponse("Error: Permission denied."));
        }
        order.setStatus(orderStatusRepository.findByName(EOrderStatus.DONE).orElseThrow());
        orderRepository.saveAndFlush(order);
        return ResponseEntity.ok(new MessageResponse("Order was successfully closed."));
    }
}
