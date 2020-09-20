package ca.com.rlsp.delivery.registration;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.eclipse.microprofile.openapi.annotations.tags.Tag;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name="restaurant")
public class RestaurantResource {

	/******************************************
	 * Restaurants 
	 ******************************************/
	
    @GET   
    public List<Restaurant> getRestaurants() {
    	return Restaurant.listAll();		           
    }
    
    @POST
    @Transactional
    public Response addRestaurant(Restaurant dto) {
    	dto.persist();
    	return Response.status(Status.CREATED).build();
    	
    }
    
    @PUT
    @Path("{id}")
    @Transactional
    public void updateestaurant(@PathParam("id") Long id, Restaurant dto) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
    	if( restaurantOptional.isEmpty()) {
    		throw new NotFoundException();
    	}
    	
    	Restaurant restaurant = restaurantOptional.get();
    	restaurant.name = dto.name;
    	
    	restaurant.persist();
    	
    }
    
    @DELETE
    @Path("{id}")
    @Transactional
    public void deleteRestaurant(@PathParam("id") Long id) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
    	
    	restaurantOptional.ifPresentOrElse(Restaurant::delete, () -> { throw new NotFoundException(); });
    	
    }
    

	/******************************************
	 * Dishes 
	 ******************************************/

    //Searching for dishes
    @GET
    @Path("{idRestaurant}/dishes")
    @Tag(name="dish")
    public List<Restaurant> getDishes(@PathParam("idRestaurant") Long idResraurant){
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idResraurant);
    	
    	if (restaurantOptional.isEmpty()) {
    		throw new NotFoundException("Restaurant does not exist");
    	}
    	
    	return Dish.list("restaurant", restaurantOptional.get());
    }
    
    @POST
    @Path("{idRestaurant/dishes")
    @Transactional
    @Tag(name="dish")
    public Response addDish(@PathParam("idRestaurant") Long idRestaurant, Dish dto) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    	
    	if ( restaurantOptional.isEmpty()) {
    		throw new NotFoundException("Restaurant does not exist");
    	}
    	
    	//Using DTO, receiving detached entity passed to persist()
    	Dish dish = new Dish();
    	dish.name = dto.name;
    	dish.description = dto.description;
    	dish.price = dto.price;
    	dish.restaurant = restaurantOptional.get();
    	
    	dish.persist();
    	
    	return Response.status(Status.CREATED).build();
    }
    
    @PUT
    @Path("{idRestaurant/dishes/{id}")
    @Transactional
    @Tag(name="dish")
    public void updateDish(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id, Dish dto) {
    	Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);
    	
    	if (restaurantOptional.isEmpty()) {
            throw new NotFoundException("Restaurant does not exist");
        }
    	
    	//This application has uniques IDs for dishes
    	Optional<Dish> dishOptional = Dish.findByIdOptional(id);
    	
    	if (dishOptional.isEmpty()) {
            throw new NotFoundException("Dish does not exist");
        }
    	
    	Dish dish = dishOptional.get();
    	dish.price = dto.price; // Just update the price
    	
    	dish.persist();
    }
    
    @DELETE
    @Path("{idRestaurant}/{dishes/{id}")
    @Tag(name="dish")
    public void deleteDish(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {
    	
    	Optional<Restaurant> restauranteOptional = Restaurant.findByIdOptional(idRestaurant);
        if (restauranteOptional.isEmpty()) {
            throw new NotFoundException("Restaurante não existe");
        }

        Optional<Dish> pratoOp = Dish.findByIdOptional(id);

        pratoOp.ifPresentOrElse(Dish::delete, () -> {
            throw new NotFoundException();
        });
    }
    
}