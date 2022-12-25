package ru.ssau.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.delivery.models.DishesInOrder;

import java.util.List;

public interface DishesInOrderRepository extends JpaRepository<DishesInOrder, Long> {
    List<DishesInOrder> findAllByOrderId(Long orderId);
}
