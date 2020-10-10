package ca.com.rlsp.delivery.model;


import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import ca.com.rlsp.delivery.dto.DishDTO;
import ca.com.rlsp.delivery.dto.DishOrderDTO;
import ca.com.rlsp.delivery.dto.OrderDoneDTO;
import ca.com.rlsp.delivery.dto.RestaurantDTO;
import io.smallrye.mutiny.Uni;
import io.vertx.mutiny.pgclient.PgPool;

@Path("cart")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CartResource {

    private static final String CLIENT = "a";

    @Inject
    PgPool clientPgPool;

    @Inject
    @Channel("orders")
    Emitter<OrderDoneDTO> emitterOrder;

    @GET
    public Uni<List<DishCart>> buscarcarrinho() {
        return DishCart.findCarrinho(clientPgPool, CLIENT);
    }

    @POST
    @Path("/dish/{idDish}")
    public Uni<Long> adicionarPrato(@PathParam("idDish") Long idDish) {
        //poderia retornar o pedido atual
        DishCart pc = new DishCart();
        pc.client = CLIENT;
        pc.dish = idDish;
        return DishCart.save(clientPgPool, CLIENT, idDish);

    }

    @POST
    @Path("/ordering")
    public Uni<Boolean> finalizar() {
        OrderDoneDTO order = new OrderDoneDTO();
        String client = CLIENT;
        order.client = client;
        List<DishCart> pratoCarrinho = DishCart.findCarrinho(clientPgPool, client).await().indefinitely();
        //Utilizar mapstruts
        List<DishOrderDTO> dishes = pratoCarrinho.stream().map(pc -> from(pc)).collect(Collectors.toList());
        order.dishes = dishes;

        RestaurantDTO Restaurant = new RestaurantDTO();
        Restaurant.name = "Restaurant Name";
        order.restaurant = Restaurant;
        emitterOrder.send(order);
        return DishCart.delete(clientPgPool, client);
    }

    private DishOrderDTO from(DishCart pratoCarrinho) {
        DishDTO dto = Dish.findById(clientPgPool, pratoCarrinho.dish).await().indefinitely();        
        return new DishOrderDTO(dto.name, dto.description, dto.price);
    }

} 