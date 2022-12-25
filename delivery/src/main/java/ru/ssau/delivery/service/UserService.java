package ru.ssau.delivery.service;

import org.springframework.security.core.Authentication;
import ru.ssau.delivery.models.User;

public interface UserService {
    User findUserByIdentifier(String identifier);
    User obtainUser(Authentication authentication);
}
