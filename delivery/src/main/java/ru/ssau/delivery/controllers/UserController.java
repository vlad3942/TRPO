package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.ssau.delivery.models.*;
import ru.ssau.delivery.pojo.*;
import ru.ssau.delivery.repository.*;
import ru.ssau.delivery.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('USER')")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RestaurantRepository restaurantRepository;
    private final DishRepository dishRepository;
    private final DishesInOrderRepository dishesInOrderRepository;
    private final OrderRepository orderRepository;
    private final OrderStatusRepository orderStatusRepository;


    @PreAuthorize("hasAnyRole('USER', 'COURIER')")
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

    @PostMapping("/make-order")
    public ResponseEntity<?> makeOrder(
            Authentication authentication,
            @RequestBody CreateOrderRequest request
    ) {
        if (request.getDishes() == null || request.getDishes().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Dishes list was empty or null."));
        }
        //Получаем юзера для привязки заказа
        User user = userService.obtainUser(authentication);
        if (user == null) {
            return ResponseEntity.badRequest().body(new MessageResponse("User was not found."));
        }
        //Создаём заказ
        Order order = new Order();
        order.setUser(user);
        //Находим рестаран для заказа и закрепляем заказ за рестораном
        Optional<Restaurant> restaurantOptional = restaurantRepository.findById(request.getRestId());
        if (restaurantOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Restaurant was not found."));
        }
        var rest = restaurantOptional.get();
        rest.setDishes(null);
        order.setRestaurant(rest);
        if (request.getDeliveryAddress() == null || request.getDeliveryAddress().isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Delivery address was empty or null."));
        }
        //Устанавливаем Адрес, Комментарий к заказу и Начальный стстус заказа
        order.setDeliveryAddress(request.getDeliveryAddress());
        order.setComment(request.getComment());
        var optStatus = orderStatusRepository.findByName(EOrderStatus.WAITING_CONFIRMATION);
        if (optStatus.isEmpty()) {
            throw new IllegalStateException("Status was not found in repository...");
        }
        order.setStatus(optStatus.get());
        List<DishesInOrder> dishesInOrder = new ArrayList<>();
        double totalPrice = 0;
        //Обрабатываем блюда из заказа
        for (var dish :
                request.getDishes()) {
            //Находим текущее блюдо из заказа и получаем последнию информацию о нём из БД
            Optional<Dish> dishOptional = dishRepository.findById(dish.getId());
            if (dishOptional.isEmpty()) {
                return ResponseEntity.badRequest().body(new MessageResponse("Dish with id: " + dish.getId() + " was not found."));
            }
            Dish current = dishOptional.get();
            //Проверяем что блюдо в заказе принадлежит к рестарану, который будет выполнять заказ
            if (!current.getRestaurant().getId().equals(request.getRestId())) {
                return ResponseEntity.badRequest().body(new MessageResponse("Dish with id: " + dish.getId() + " was not found in restaurant with id: " + request.getRestId() + "."));
            }
            current.getRestaurant().setDishes(null);
            //Проверяем что количество блюд в заказе больше
            if (dish.getAmount() <= 0) {
                continue;
            }
            //Сообщаем об ошибке, если количество блюд в заказе превышает текущий остаток
            if (current.getAmount() < dish.getAmount()) {
                return ResponseEntity.badRequest().body(new MessageResponse("There are not enough dishes in the restaurant, please refresh the page and reorder."));
            }
            //Создаём объект для связи блюда и заказа
            DishesInOrder dio = new DishesInOrder();
            dio.setAmount(dish.getAmount());
            dio.setOrder(order);
            dio.setDish(current);
            dishesInOrder.add(dio);
            //Рассчитываем общую стоимость заказа.
            totalPrice += dish.getAmount() * current.getPrice();
        }
        //Если в заказе нет блюд (если amount везде оказался равным нулю) сообщаем об ошибке
        if (dishesInOrder.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Empty order..."));
        }
        order.setTotalPrice(totalPrice);
        //Сохраняем заказ
        Order orderRes = orderRepository.saveAndFlush(order);
        dishesInOrder.forEach(dio -> {
            //Изменяем количество доступных для заказа блюд в соответствии с количеством запасов на складе
            dishRepository.updateAmount(dio.getDish().getId(), dio.getAmount());
            dio.setOrder(orderRes);
        });
        //Добавляем зарезервированные в заказе блюда
        dishesInOrderRepository.saveAllAndFlush(dishesInOrder);
        return ResponseEntity.ok(new CreateOrderResponse(orderRes.getId(), "Order was successfully created."));
    }

    @GetMapping("/orders")
    public List<OrderResponse> getOrders(Authentication authentication) {
        User user = userService.obtainUser(authentication);
        List<Order> result = orderRepository.findAllByUser(user);
        return OrderResponse.convert(result);
    }

    @GetMapping("/orders/{order_id}")
    public ResponseEntity<?> getOrder(Authentication authentication, @PathVariable("order_id") Long id) {
        User user = userService.obtainUser(authentication);
        Order result = orderRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order with id: " + id));
        if (!result.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Permission failed"));
        }
        List<DishesInOrder> dios = dishesInOrderRepository.findAllByOrderId(result.getId());
        OrderResponse response = OrderResponse.convert(result);
        dios.forEach(d -> response.getDishes().add(DishInfo.convert(d)));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/orders/{order_id}/cancel")
    public ResponseEntity<?> cancelOrder(
            Authentication authentication,
            @PathVariable("order_id") Long orderId
    ) {
        User user = userService.obtainUser(authentication);
        Order result = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId));
        if (!result.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Permission failed"));
        }
        if (!result.getStatus().getName().equals(EOrderStatus.WAITING_CONFIRMATION)) {
            return ResponseEntity.badRequest().body(
                    new MessageResponse("The order cannot be closed because it has already been confirmed.")
            );
        }
        result.setStatus(orderStatusRepository.findByName(EOrderStatus.CANCELED).orElseThrow());
        orderRepository.saveAndFlush(result);
        return ResponseEntity.ok(new MessageResponse("Order was successfully canceled."));
    }

    @GetMapping("/orders/{order_id}/warning")
    public ResponseEntity<?> warningOrder(
            Authentication authentication,
            @PathVariable("order_id") Long orderId
    ) {
        User user = userService.obtainUser(authentication);
        Order result = orderRepository.findById(orderId).orElseThrow(() -> new EntityNotFoundException("Order with id: " + orderId));
        if (!result.getUser().getId().equals(user.getId())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Permission failed"));
        }
        result.setStatus(orderStatusRepository.findByName(EOrderStatus.WARNING).orElseThrow());
        return ResponseEntity.ok(new MessageResponse("Order was successfully warned."));
    }
}
