package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import ru.ssau.delivery.models.*;
import ru.ssau.delivery.pojo.*;
import ru.ssau.delivery.repository.*;
import ru.ssau.delivery.service.UserService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@RestController
@PreAuthorize("hasRole('REST')")
@RequestMapping("/api/rest")
@RequiredArgsConstructor
public class OwnerController {

    private final UserService userService;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;

    private final DishesInOrderRepository dishesInOrderRepository;

    @PostMapping("/restaurant/new")
    public ResponseEntity<?> createNewRestaurant(Authentication authentication, @RequestBody CreateRestaurantRequest request) {
        User user = userService.obtainUser(authentication);
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

    @GetMapping("/restaurant/{restaurant_id}")
    public ResponseEntity<?> setConfirmRestaurantById(
            Authentication authentication,
            @PathVariable("restaurant_id") Long id,
            @RequestParam(name = "open", defaultValue = "false") boolean isOpen
    ) {
        var res = obtainRestaurant(authentication, id);
        if (res.entity != null) {
            return res.entity;
        }
        Restaurant r = res.restaurant;
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
        var res = obtainRestaurant(authentication, id);
        if (res.entity != null) {
            return res.entity;
        }
        Restaurant r = res.restaurant;
        Dish d = new Dish();
        d.setRestaurant(r);
        d.setName(request.getName());
        d.setAmount(request.getAmount());
        d.setPrice(request.getPrice());
        d.setDescription(request.getDescription());
        d = dishRepository.saveAndFlush(d);
        return ResponseEntity.ok(new MessageResponse("Dish was successfully added in Restaurant with restaurant_id: " + id + ", dish_id: " + d.getId()));
    }

    @PostMapping("/restaurants/{restaurant_id}/addDishes")
    public ResponseEntity<?> addDishes(
            Authentication authentication,
            @PathVariable("restaurant_id") Long id,
            @RequestBody List<CreateDishRequest> request
    ) {
        var res = obtainRestaurant(authentication, id);
        if (res.entity != null) {
            return res.entity;
        }
        Restaurant r = res.restaurant;
        for (var dish :
                request) {
            Dish d = new Dish();
            d.setRestaurant(r);
            d.setName(dish.getName());
            d.setAmount(dish.getAmount());
            d.setPrice(dish.getPrice());
            d.setDescription(dish.getDescription());
            dishRepository.saveAndFlush(d);
        }
        return ResponseEntity.ok(new MessageResponse("Dishes was successfully added in Restaurant with restaurant_id: " + id));
    }

    @GetMapping("/restaurants/{restaurant_id}/get-not-confirmed-orders")
    public ResponseEntity<?> getNotConfirmed(Authentication authentication, @PathVariable("restaurant_id") Long id) {
        var res = obtainRestaurant(authentication, id);
        if (res.entity != null) {
            return res.entity;
        }
        Restaurant r = res.restaurant;

        OrderStatus status = orderStatusRepository.findByName(EOrderStatus.WAITING_CONFIRMATION)
                .orElseThrow(() -> new IllegalStateException("Status s not found in database."));
        List<Order> unconfirmedOrders = orderRepository.findAllByRestaurantAndStatus(r, status);
        List<OrderResponse> orderResponses = OrderResponse.convert(unconfirmedOrders);
        orderResponses.forEach(orderResponse -> {
            List<DishesInOrder> dios = dishesInOrderRepository.findAllByOrderId(orderResponse.getId());
            orderResponse.setDishes(
                    dios.stream().map(DishInfo::convert)
                            .collect(Collectors.toList()));
        });
        return ResponseEntity.ok(orderResponses);
    }

    @GetMapping("/restaurants/{restaurant_id}/confirm-order/{order_id}")
    public ResponseEntity<?> confirmOrder(
            Authentication authentication,
            @PathVariable("order_id") Long orderId,
            @PathVariable("restaurant_id") Long restaurantId
    ) {
        var res = obtainRestaurant(authentication, restaurantId);
        if (res.entity != null) {
            return res.entity;
        }
        Restaurant restaurant = res.restaurant;
        Order order = orderRepository.findById(orderId).orElseThrow();
        if (!order.getRestaurant().getId().equals(restaurant.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Confirmation Error - Permission Denied."));
        }
        var status = orderStatusRepository.findByName(EOrderStatus.WAITING_COURIER).orElseThrow();
        order.setStatus(status);
        Order result = orderRepository.saveAndFlush(order);
        return ResponseEntity.ok(new MessageResponse("Order with id: " + result.getId() + " was confirmed successfully."));
    }

    private RestObtainEntity obtainRestaurant(Authentication authentication, Long id) {
        RestObtainEntity response = new RestObtainEntity();
        User user = userService.obtainUser(authentication);
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            response.entity = ResponseEntity.badRequest().body(new MessageResponse("Error: can not found restaurant by id - " + id));
            return response;
        }
        Restaurant r = byId.get();
        if (!r.getOwnerId().equals(user.getId())) {
            response.entity = ResponseEntity.badRequest().body(new MessageResponse("Error: can not found restaurant by id - " + id));
            return response;
        }
        response.restaurant = r;
        return response;
    }

    private static class RestObtainEntity {
        private ResponseEntity<?> entity;
        private Restaurant restaurant;
    }
}
