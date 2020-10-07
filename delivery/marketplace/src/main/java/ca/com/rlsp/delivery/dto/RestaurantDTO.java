package ca.com.rlsp.delivery.dto;

import java.math.BigDecimal;

import io.vertx.mutiny.sqlclient.Row;

public class RestaurantDTO {
	
	public Long id;

    public String name;

    public String description;

    public BigDecimal price;

    public static DishDTO from(Row row) {
    	DishDTO dto = new DishDTO();
        dto.description = row.getString("description");
        dto.name = row.getString("name");
        dto.id = row.getLong("id");
        dto.price = row.getBigDecimal("price");
        return dto;
    }
}
