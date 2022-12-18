package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ssau.delivery.models.Dish;
import ru.ssau.delivery.models.Restaurant;
import ru.ssau.delivery.models.User;
import ru.ssau.delivery.pojo.CreateDishRequest;
import ru.ssau.delivery.pojo.CreateRestaurantRequest;
import ru.ssau.delivery.pojo.MessageResponse;
import ru.ssau.delivery.repository.DishRepository;
import ru.ssau.delivery.repository.RestaurantRepository;
import ru.ssau.delivery.service.UserService;

import java.util.Optional;


@Slf4j
@RestController
@RequestMapping("/api/rest")
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;

    @PreAuthorize("hasRole('REST')")
    @PostMapping("/restaurant/new")
    public ResponseEntity<?> createNewRestaurant(Authentication authentication, @RequestBody CreateRestaurantRequest request) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User user = userService.findUserByIdentifier(userDetails.getUsername());
        long ownerId = user.getId();

        Restaurant newRestaurant = new Restaurant();
        newRestaurant.setName(request.getRestaurantName());
        newRestaurant.setOwnerId(ownerId);
        newRestaurant.setAddress(request.getAddress());
        newRestaurant.setOpenTime(request.getOpenTime());
        newRestaurant.setCloseTime(request.getCloseTime());
        try {
            var rest = restaurantRepository.saveAndFlush(newRestaurant);
            if (rest.getId() == null) {
                return ResponseEntity.badRequest().body(new MessageResponse("Creating new restaurant failure"));
            }
            return ResponseEntity.ok(new MessageResponse("New restaurant was successfully created: " + rest.getId()));
        } catch (Exception e) {
            log.error("Creating new restaurant failure", e);
            return ResponseEntity.badRequest().body(new MessageResponse("Creating new restaurant failure: " + e.getMessage()));
        }
    }

    @PreAuthorize("hasRole('REST')")
    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<?> setConfirmRestaurantById(
            Authentication authentication,
            @PathVariable("restaurant_id") Long id,
            @RequestParam(name = "open", defaultValue = "false") boolean isOpen
    ) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        User user = userService.findUserByIdentifier(username);
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: can not found restaurant by id - " + id));
        }
        Restaurant r = byId.get();
        if (!r.getOwnerId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: can not found restaurant by id - " + id));
        }
        r.setIsOpen(isOpen);
        restaurantRepository.save(r);
        return ResponseEntity.ok(new MessageResponse("Restaurant with id: " + id + " was successfully opened."));
    }

    @PostMapping("/restaurants/{restaurant_id}/addDish")
    public ResponseEntity<?> addDish(
            Authentication authentication,
            @PathVariable("restaurant_id") Long id,
            @RequestBody CreateDishRequest request
    ) {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: can not found restaurant by id - " + id));
        }
        Restaurant r = byId.get();
        Dish d = new Dish();
        d.setRestaurant(r);
        d.setName(request.getName());
        d.setAmount(request.getAmount());
        d.setPrice(request.getPrice());
        d.setDescription(request.getDescription());
        d = dishRepository.saveAndFlush(d);
        return ResponseEntity.ok(new MessageResponse("Dish was successfully added in Restaurant with restaurant_id: " + id + ", dish_id: " + d.getId()));
    }
}
