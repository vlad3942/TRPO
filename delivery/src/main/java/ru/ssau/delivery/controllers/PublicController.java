package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.ssau.delivery.models.Dish;
import ru.ssau.delivery.models.Restaurant;
import ru.ssau.delivery.repository.DishRepository;
import ru.ssau.delivery.repository.RestaurantRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/public")
@RequiredArgsConstructor
public class PublicController {

    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants(
            @RequestParam(name = "limit", defaultValue = "100") int limit,
            @RequestParam(name = "skip", defaultValue = "0") int skip
    ) {
        return restaurantRepository.getOpenedLimitedRestaurants(skip, limit);
    }

    @GetMapping("/restaurants/{rest_id}")
    public Restaurant getRestaurant(
            @PathVariable("rest_id") Long id
    ) {
        return restaurantRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Restaurant with id: " + id + " was not found."));
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
