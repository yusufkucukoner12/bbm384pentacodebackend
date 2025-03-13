package pentacode.backend.code.restaurant.service;
import java.util.List;

import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pentacode.backend.code.common.service.BaseService;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.mapper.RestaurantMapper;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
@Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class RestaurantService extends BaseService<Restaurant>{
    public RestaurantMapper restaurantMapper;
    public RestaurantRepository restaurantRepository;

    public RestaurantDTO getRestaurant(Long pk){
        return restaurantMapper.mapToDTO(restaurantRepository.findByPk(pk));
    }

    public List<RestaurantDTO> getRestaurantByName(String name){
        return restaurantMapper.mapToListDTO(restaurantRepository.findByName(name));
    }
}