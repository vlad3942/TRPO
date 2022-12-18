package ru.ssau.delivery.service;

import org.springframework.security.core.Authentication;
import ru.ssau.delivery.models.Restaurant;

public interface RestaurantService {
    Restaurant getRestaurantByIdAndAuthentication(Long id, Authentication authentication);
}
