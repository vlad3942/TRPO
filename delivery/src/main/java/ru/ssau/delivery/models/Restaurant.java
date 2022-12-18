package ru.ssau.delivery.models;

import lombok.Data;

import javax.persistence.*;
import java.time.OffsetDateTime;
import java.util.Set;

@Data
@Entity
@Table(name = "restaurant", uniqueConstraints = {@UniqueConstraint(columnNames = "address")})
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String address;
    private Double rating;

    @Column(name = "owner_id", nullable = false)
    private Long ownerId;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
    private Set<Dish> dishes;

    private OffsetDateTime openTime;
    private OffsetDateTime closeTime;

    @Column(nullable = false)
    private Boolean isOpen = false;
    @Column(nullable = false)
    private Boolean IsConfirmed = false;
}
