package pentacode.backend.code.restaurant.service;
import java.util.List;

import org.springframework.stereotype.Service;

import pentacode.backend.code.common.service.BaseService;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.mapper.RestaurantMapper;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
public class RestaurantService extends BaseService<Restaurant>{
    private final  RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    
    public RestaurantService(RestaurantMapper restaurantMapper, RestaurantRepository restaurantRepository){
        super(restaurantRepository);
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public RestaurantDTO getRestaurant(Long pk){
        return restaurantMapper.mapToDTO(restaurantRepository.findByPk(pk));
    }

    public List<RestaurantDTO> getRestaurantByName(String name){
        return restaurantMapper.mapToListDTO(restaurantRepository.findByName(name));
    }
}