package ca.com.rlsp.delivery.order;

import java.math.BigDecimal;

public class OrderDishesDTO {

	public String name;

    public String description;

    public BigDecimal price;

    public OrderDishesDTO() {
        super();
    }

    public OrderDishesDTO(String name, String description, BigDecimal price) {
        super();
        this.name = name;
        this.description = description;
        this.price = price;
    }

}
