package ru.ssau.delivery.pojo;

import lombok.Value;

import java.time.OffsetDateTime;

@Value
public class CreateRestaurantRequest {
    String restaurantName;
    String address;
    OffsetDateTime openTime;
    OffsetDateTime closeTime;
}
