package ca.com.rlsp.delivery.registration.model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.json.bind.Jsonb;
import javax.json.bind.JsonbBuilder;
import javax.transaction.Transactional;
import javax.validation.Valid;
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

import org.eclipse.microprofile.jwt.Claim;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.SimplyTimed;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlow;
import org.eclipse.microprofile.openapi.annotations.security.OAuthFlows;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirement;
import org.eclipse.microprofile.openapi.annotations.security.SecurityRequirements;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import ca.com.rlsp.delivery.registration.dto.AddDishDTO;
import ca.com.rlsp.delivery.registration.dto.AddRestaurantDTO;
import ca.com.rlsp.delivery.registration.dto.DishDTO;
import ca.com.rlsp.delivery.registration.dto.RestaurantDTO;
import ca.com.rlsp.delivery.registration.dto.UpdateDishPriceDTO;
import ca.com.rlsp.delivery.registration.dto.UpdateRestaurantDTO;
import ca.com.rlsp.delivery.registration.mapper.DishMapper;
import ca.com.rlsp.delivery.registration.mapper.RestaurantMapper;
import ca.com.rlsp.delivery.registration.utils.ConstraintViolationResponse;
import io.quarkus.security.ForbiddenException;

@Path("/restaurants")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Tag(name = "restaurant")
@RolesAllowed("owner")
@SecurityScheme(securitySchemeName = "delivery-oauth", type = SecuritySchemeType.OAUTH2, flows = @OAuthFlows(password = @OAuthFlow(tokenUrl = "http://localhost:8180/auth/realms/delivery/protocol/openid-connect/token")))
//@SecurityRequirement(name="delivery-oauth")
@SecurityRequirements(value = { @SecurityRequirement(name = "delivery-oauth", scopes = {}) })
//@SecurityRequirements(value = {@SecurityRequirement(name = "delivery-oauth")})
public class RestaurantResource {

	@Inject
	@Channel("restaurants")
	Emitter<Restaurant> emitter;

	@Inject
	JsonWebToken jwt;

	@Inject
	@Claim(standard = Claims.sub)
	String sub;

	@Inject
	RestaurantMapper restaurantMapper;

	@Inject
	DishMapper dishMapper;

	/******************************************
	 * Restaurants
	 ******************************************/

	@GET
	@Counted(name = "Search amount for Restaurants", description = "Counter for Restaurants searching on API")
	@SimplyTimed(name = "name", description = "Computing the time spent to run a searching for Restaurants")
	@Timed(name = "All computing time for do a searching")
	public List<RestaurantDTO> getRestaurants() {
		Stream<Restaurant> restaurants = Restaurant.streamAll();
		return restaurants.map(r -> restaurantMapper.toRestaurantDTO(r)).collect(Collectors.toList());
	}

	@POST
	@Transactional
	@APIResponse(responseCode = "201", description = "Restaurant added Successfully")
	@APIResponse(responseCode = "400", content = @Content(schema = @Schema(allOf = ConstraintViolationResponse.class))) // Print
																														// violations/constraints/error
																														// in
																														// JSON
	public Response addRestaurant(@Valid AddRestaurantDTO dto) {
		Restaurant restaurant = restaurantMapper.toRestaurant(dto);
		restaurant.owner = sub;
		restaurant.persist();
//		Jsonb jsonb = JsonbBuilder.create();
//		String json = jsonb.toJson(restaurant);
//		emitter.send(json);
		emitter.send(restaurant);
		// emitter.send(restaurant);
		return Response.status(Status.CREATED).build();

	}

	@PUT
	@Path("{id}")
	@Transactional
	public void updateestaurant(@PathParam("id") Long id, UpdateRestaurantDTO dto) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException();
		}

		Restaurant restaurant = restaurantOptional.get();
	    
		if (!restaurant.owner.equals(sub)) {
            throw new ForbiddenException();
        }

		// MapStruct: aqui passo a referencia para ser atualizada
		restaurantMapper.toRestaurant(dto, restaurant);

		restaurant.persist();

	}

	@DELETE
	@Path("{id}")
	@Transactional
	public void deleteRestaurant(@Valid @PathParam("id") Long id) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(id);
		if (!restaurantOptional.get().owner.equals(sub)) {
            throw new ForbiddenException();
        }

		restaurantOptional.ifPresentOrElse(Restaurant::delete, () -> {
			throw new NotFoundException();
		});

	}

	/******************************************
	 * Dishes
	 ******************************************/

	// Searching for dishes
	@GET
	@Path("{idRestaurant}/dishes")
	@Tag(name = "dish")
	public List<DishDTO> getDishes(@PathParam("idRestaurant") Long idResraurant) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idResraurant);

		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant does not exist");
		}

		Stream<Dish> dish = Dish.stream("restaurant", restaurantOptional.get());
		return dish.map(p -> dishMapper.toDTO(p)).collect(Collectors.toList());

	}

	@POST
	@Path("{idRestaurant/dishes")
	@Transactional
	@Tag(name = "dish")
	public Response addDish(@PathParam("idRestaurant") Long idRestaurant, AddDishDTO dto) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);

		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant does not exist");
		}

		// Using DTO, receiving detached entity passed to persist()
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
	@Tag(name = "dish")
	public void updateDish(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id,
			UpdateDishPriceDTO dto) {
		Optional<Restaurant> restaurantOptional = Restaurant.findByIdOptional(idRestaurant);

		if (restaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant does not exist");
		}

		// This application has uniques IDs for dishes
		Optional<Dish> dishOptional = Dish.findByIdOptional(id);

		if (dishOptional.isEmpty()) {
			throw new NotFoundException("Dish does not exist");
		}

		Dish dish = dishOptional.get();
		dishMapper.toDish(dto, dish); // Just update the price

		dish.persist();
	}

	@DELETE
	@Path("{idRestaurant}/{dishes/{id}")
	@Tag(name = "dish")
	public void deleteDish(@PathParam("idRestaurant") Long idRestaurant, @PathParam("id") Long id) {

		Optional<Restaurant> RestaurantOptional = Restaurant.findByIdOptional(idRestaurant);
		if (RestaurantOptional.isEmpty()) {
			throw new NotFoundException("Restaurant does not exist");
		}

		Optional<Dish> pratoOp = Dish.findByIdOptional(id);

		pratoOp.ifPresentOrElse(Dish::delete, () -> {
			throw new NotFoundException();
		});
	}

}