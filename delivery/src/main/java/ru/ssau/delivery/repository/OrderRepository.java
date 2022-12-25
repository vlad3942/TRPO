package ru.ssau.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.delivery.models.Order;
import ru.ssau.delivery.models.OrderStatus;
import ru.ssau.delivery.models.Restaurant;
import ru.ssau.delivery.models.User;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByUser(User user);
    List<Order> findAllByRestaurantAndStatus(Restaurant restaurant, OrderStatus status);
    List<Order> findAllByRestaurantIdAndStatus(Long restaurantId, OrderStatus status);
}
