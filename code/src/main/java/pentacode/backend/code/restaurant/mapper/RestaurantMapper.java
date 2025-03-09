package pentacode.backend.code.restaurant.mapper;
import org.mapstruct.Mapper;

import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Restaurant;



@Mapper(componentModel="spring")
public interface RestaurantMapper {
    Restaurant mapToEntity(RestaurantDTO restaurantDTO);
    RestaurantDTO mapToDTO(Restaurant restaurant);    
}
