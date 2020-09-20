package ca.com.rlsp.delivery.registration.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import ca.com.rlsp.delivery.registration.Dish;
import ca.com.rlsp.delivery.registration.dto.AddDishDTO;
import ca.com.rlsp.delivery.registration.dto.DishDTO;
import ca.com.rlsp.delivery.registration.dto.UpdateDishPriceDTO;

@Mapper(componentModel = "cdi")
public interface DishMapper {

    DishDTO toDTO(Dish d);

    Dish toPrato(AddDishDTO dto);

    void toPrato(UpdateDishPriceDTO dto, @MappingTarget Dish dish);

}