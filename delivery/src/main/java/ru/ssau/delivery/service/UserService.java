package ru.ssau.delivery.service;

import ru.ssau.delivery.models.User;

public interface UserService {
    User findUserByIdentifier(String identifier);
}
