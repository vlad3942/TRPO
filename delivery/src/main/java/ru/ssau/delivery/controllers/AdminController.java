package ru.ssau.delivery.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import ru.ssau.delivery.models.*;
import ru.ssau.delivery.pojo.MessageResponse;
import ru.ssau.delivery.pojo.RegisterCourierRequest;
import ru.ssau.delivery.pojo.SignupRequest;
import ru.ssau.delivery.repository.CourierRepository;
import ru.ssau.delivery.repository.RestaurantRepository;
import ru.ssau.delivery.repository.RoleRepository;
import ru.ssau.delivery.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class AdminController {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RestaurantRepository restaurantRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourierRepository courierRepository;


    @PostMapping("/register/restaurant-owner")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signupRequest) {

        if (userRepository.existsByUsername(signupRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        if (userRepository.existsByPhone(signupRequest.getPhone())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Phone number is exist"));
        }

        User user = new User(
                signupRequest.getUsername(),
                signupRequest.getEmail(),
                signupRequest.getPhone(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Role userRole = roleRepository
                .findByName(ERole.ROLE_REST)
                .orElseThrow(() -> new RuntimeException("Error, Role REST is not found"));
        Set<Role> roles = Collections.singleton(userRole);
        user.setRoles(roles);
        userRepository.saveAndFlush(user);
        return ResponseEntity.ok(new MessageResponse("Restaurant owner CREATED"));
    }

    @GetMapping("/restaurants/{restaurant_id}")
    public ResponseEntity<?> setConfirmRestaurantById(
            @PathVariable("restaurant_id") Long id,
            @RequestParam(name = "confirmed", defaultValue = "false") boolean isConfirmed
    ) {
        Optional<Restaurant> byId = restaurantRepository.findById(id);
        if (byId.isEmpty()) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: can not found restaurant by id - " + id));
        }
        Restaurant r = byId.get();
        r.setIsConfirmed(isConfirmed);
        restaurantRepository.save(r);
        return ResponseEntity.ok(new MessageResponse("Restaurant with id: " + id + " was successfully confirmed."));
    }

    @PostMapping("/register/courier")
    public ResponseEntity<?> registerCourier(@RequestBody RegisterCourierRequest request) {
        if (userRepository.existsByUsername(request.getUserData().getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is exist"));
        }

        if (userRepository.existsByEmail(request.getUserData().getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is exist"));
        }

        if (userRepository.existsByPhone(request.getUserData().getPhone())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Phone number is exist"));
        }

        User user = new User(
                request.getUserData().getUsername(),
                request.getUserData().getEmail(),
                request.getUserData().getPhone(),
                passwordEncoder.encode(request.getUserData().getPassword()));

        Role userRole = roleRepository
                .findByName(ERole.ROLE_COURIER)
                .orElseThrow(() -> new RuntimeException("Error, Role COURIER is not found"));
        Set<Role> roles = Collections.singleton(userRole);
        user.setRoles(roles);
        user = userRepository.saveAndFlush(user);
        Courier courier = new Courier();
        courier.setUser(user);
        courier.setName(request.getCourierInfo().getName());
        courier.setForNotes(request.getCourierInfo().getForNotes());
        courier.setPassportDetails(request.getCourierInfo().getPassportDetails());
        courier.setIsFree(true);
        courierRepository.saveAndFlush(courier);
        return ResponseEntity.ok(new MessageResponse("Courier was successfully CREATED"));
    }
}

