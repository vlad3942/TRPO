package ru.ssau.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.delivery.models.Dish;

public interface DishRepository extends JpaRepository <Dish, Long> {
}
