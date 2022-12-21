package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.ssau.delivery.models.Dish;
import ru.ssau.delivery.models.Restaurant;
import ru.ssau.delivery.repository.DishRepository;
import ru.ssau.delivery.repository.RestaurantRepository;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class UserController {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestList(
            @RequestParam(name = "limit", defaultValue = "100") int limit,
            @RequestParam(name = "skip", defaultValue = "0") int skip
    ) {
        return restaurantRepository.getLimitedRestaurants(skip, limit);
    }

    @GetMapping("/restaurants/{rest_id}/dish")
    public List<Dish> getDishesListForRestrant(
            @PathVariable("rest_id") long restId,
            @RequestParam(name = "limit", defaultValue = "100") int limit,
            @RequestParam(name = "skip", defaultValue = "0") int skip
    ) {
        return dishRepository.getLimitedDishes(restId, skip, limit);
    }
}
