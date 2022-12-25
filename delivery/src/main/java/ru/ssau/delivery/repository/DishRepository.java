package ru.ssau.delivery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.ssau.delivery.models.Dish;
import ru.ssau.delivery.models.Restaurant;

import java.util.List;

public interface DishRepository extends JpaRepository <Dish, Long> {
    @Query(nativeQuery = true, value = "SELECT * FROM public.dish where restaurant_id = :restId LIMIT :volume OFFSET :skip")
    List<Dish> getLimitedDishes(@Param("restId") long restId, @Param("skip") int skip, @Param("volume") int volume);
    @Query(nativeQuery = true, value = "UPDATE public.dish SET amount = (amount - :delta) WHERE id = :id RETURNING id")
    Long updateAmount(@Param("id") Long id, @Param("delta") Integer amount);
}
