package ca.com.rlsp.delivery.order;

import java.util.ArrayList;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;

import org.bson.types.Decimal128;
import org.eclipse.microprofile.reactive.messaging.Incoming;

@ApplicationScoped
public class OrderedDoneIncoming {

    @Inject
    ESService elastic;
    
	@Incoming("orders")	
    @Transactional    
    public void readOrders(OrderDoneDTO dto) {
        System.out.println("-----------------");
        System.out.println(dto);

        Order p = new Order();
        p.client = dto.client;
        p.dishes = new ArrayList<>();
        dto.dishes.forEach(prato -> p.dishes.add(from(prato)));
        Restaurant restaurant = new Restaurant();
        restaurant.name = dto.restaurant.name;
        p.restaurant = restaurant;
        String json = JsonbBuilder.create().toJson(dto);
        elastic.index("orders", json);
        p.persist();

    }

    private Dish from(OrderDishesDTO dish) {
        Dish p = new Dish();
        p.description = dish.description;
        p.name = dish.name;
        p.price = new Decimal128(dish.price);
        return p;
    }
}
