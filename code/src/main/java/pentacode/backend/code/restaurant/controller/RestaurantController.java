package pentacode.backend.code.restaurant.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.mapper.RestaurantMapper;
import pentacode.backend.code.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {
    @Autowired
    private final RestaurantService restaurantService;
    @Autowired
    private final RestaurantMapper restaurantMapper;
    @GetMapping("{pk}")
    public RestaurantDTO getRestaurant(@PathVariable Long pk){
        return restaurantService.getRestaurant(pk);
    }

    @GetMapping("test")
    public RestaurantDTO test(){
        Restaurant restaurant = restaurantService.findByPkOr404(2L);
        return restaurantMapper.mapToDTO(restaurant);
    }

    @GetMapping("name/{name}")
    public RestaurantDTO getRestaurantByName(@PathVariable String name){
        return restaurantService.getRestaurantByName(name);
    }
}
