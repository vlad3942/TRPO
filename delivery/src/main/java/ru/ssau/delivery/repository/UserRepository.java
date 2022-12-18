package ru.ssau.delivery.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.ssau.delivery.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByPhone(String phone);
	Optional<User> findByEmail(String email);
	Boolean existsByUsername(String username);
	Boolean existsByEmail(String email);
	Boolean existsByPhone(String phone);
}
