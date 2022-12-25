package ru.ssau.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ssau.delivery.models.Courier;
import ru.ssau.delivery.models.User;

import java.util.Optional;

public interface CourierRepository extends JpaRepository<Courier, Long> {
    Optional<Courier> findByUser(User user);
}
