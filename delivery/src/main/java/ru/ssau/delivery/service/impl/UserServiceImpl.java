package ru.ssau.delivery.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import ru.ssau.delivery.models.User;
import ru.ssau.delivery.repository.UserRepository;
import ru.ssau.delivery.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findUserByIdentifier(String identifier) {
        Optional<User> user = userRepository.findByUsername(identifier);
        if (user.isPresent()) {
            return user.get();
        }
        user = userRepository.findByEmail(identifier);
        if (user.isPresent()) {
            return user.get();
        }
        user = userRepository.findByPhone(identifier);
        return user.orElseThrow(() -> new EntityNotFoundException("The user with the given identifier was not found"));
    }

    @Override
    public User obtainUser(Authentication authentication) {
        String username = ((UserDetails) authentication.getPrincipal()).getUsername();
        return findUserByIdentifier(username);
    }
}
