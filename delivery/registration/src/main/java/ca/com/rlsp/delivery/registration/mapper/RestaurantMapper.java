package ca.com.rlsp.delivery.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import ca.com.rlsp.delivery.registration.dto.AddRestaurantDTO;
import ca.com.rlsp.delivery.registration.dto.RestaurantDTO;
import ca.com.rlsp.delivery.registration.model.Restaurant;

@Mapper(componentModel = "cdi")
public interface RestaurantMapper {
	
	@Mapping(target = "name", source = "name_on_CRA")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "updatedDate", ignore = true)
    @Mapping(target = "locale.id", ignore = true)
    public Restaurant toRestaurant(AddRestaurantDTO dto);

	@Mapping(target="nome", source="name_on_CRA")
	public Restaurant toRestaurant(AddRestaurantDTO dto, @MappingTarget Restaurant restaurant);
	
	@Mapping(target = "nomeFantasia", source = "nome")
    //EFormatting example
    @Mapping(target = "createdDate", dateFormat = "dd/MM/yyyy HH:mm:ss")
    public RestaurantDTO toRestaurantDTO(Restaurant r);
}
