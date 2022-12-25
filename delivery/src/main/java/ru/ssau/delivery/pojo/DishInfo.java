package ru.ssau.delivery.pojo;

import lombok.Data;
import ru.ssau.delivery.models.Dish;
import ru.ssau.delivery.models.DishesInOrder;

@Data
public class DishInfo {

    private Long id;
    private String name;
    private String description;
    private Integer amount;
    private Double price;

    public static DishInfo convert(DishesInOrder dishesInOrder) {
        Dish dish = dishesInOrder.getDish();
        DishInfo dishInfo = new DishInfo();
        dishInfo.id = dish.getId();
        dishInfo.amount = dishesInOrder.getAmount();
        dishInfo.name = dish.getName();
        dishInfo.price = dish.getPrice();
        dishInfo.description = dish.getDescription();
        return dishInfo;
    }
}
