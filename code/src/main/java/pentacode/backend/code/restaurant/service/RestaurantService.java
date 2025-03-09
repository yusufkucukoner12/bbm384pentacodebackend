package pentacode.backend.code.restaurant.service;
import org.springframework.stereotype.Service;

import pentacode.backend.code.common.service.BaseService;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.mapper.RestaurantMapper;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantService extends BaseService<Restaurant>{
    public final RestaurantMapper restaurantMapper;
    public final RestaurantRepository restaurantRepository;

    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository) {
        super(restaurantRepository);
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantDTO getRestaurant(Long pk){
        return restaurantMapper.mapToDTO(restaurantRepository.findByPk(pk));
    }

    public RestaurantDTO getRestaurantByName(String name){
        return restaurantMapper.mapToDTO(restaurantRepository.findByName(name));
    }
}