package ru.ssau.delivery.pojo;

import lombok.Data;

import java.time.OffsetDateTime;

@Data
public class CreateRestaurantRequest {
    private String restaurantName;
    private String address;
    private OffsetDateTime openTime;
    private OffsetDateTime closeTime;
}
