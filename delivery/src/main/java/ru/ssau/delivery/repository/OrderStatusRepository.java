package ru.ssau.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.delivery.models.EOrderStatus;
import ru.ssau.delivery.models.OrderStatus;

import java.util.Optional;

public interface OrderStatusRepository extends JpaRepository<OrderStatus, Long> {
    Optional<OrderStatus> findByName(EOrderStatus name);
}
