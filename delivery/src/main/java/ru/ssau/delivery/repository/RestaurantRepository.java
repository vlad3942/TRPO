package ru.ssau.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.delivery.models.Restaurant;

import java.util.List;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM public.restaurant LIMIT :volume OFFSET :skip")
    List<Restaurant> getLimitedRestaurants(@Param("skip") int skip, @Param("volume") int volume);

    @Query(nativeQuery = true, value = "SELECT * FROM public.restaurant WHERE is_open AND is_confirmed LIMIT :volume OFFSET :skip")
    List<Restaurant> getOpenedLimitedRestaurants(@Param("skip") int skip, @Param("volume") int volume);
}
