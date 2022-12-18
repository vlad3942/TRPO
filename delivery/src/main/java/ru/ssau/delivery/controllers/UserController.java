package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.ssau.delivery.models.Restaurant;
import ru.ssau.delivery.repository.RestaurantRepository;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class UserController {

    private final RestaurantRepository restaurantRepository;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestList(
            @RequestParam(name = "limit", defaultValue = "100") int limit,
            @RequestParam(name = "skip", defaultValue = "0") int skip
    ) {
        return restaurantRepository.getLimitedRestaurants(skip, limit);
    }
}
