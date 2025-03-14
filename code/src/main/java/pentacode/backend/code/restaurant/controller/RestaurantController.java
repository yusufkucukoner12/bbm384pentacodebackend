package pentacode.backend.code.restaurant.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import pentacode.backend.code.common.controller.BaseController;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.mapper.RestaurantMapper;
import pentacode.backend.code.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController extends BaseController<Restaurant ,RestaurantMapper> {
    private final RestaurantService restaurantService;
    private final RestaurantMapper restaurantMapper;

    // constructor
    public RestaurantController(RestaurantService restaurantService, RestaurantMapper restaurantMapper){
        super(restaurantService, restaurantMapper);
        this.restaurantService = restaurantService;
        this.restaurantMapper = restaurantMapper;
    }

    
    @GetMapping("{pk}")
    public ResponseEntity<Object> getRestaurant(@PathVariable Long pk){
        return super.getByPkOr404(pk);
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Object> getRestaurantByName(@PathVariable String name){
        List<RestaurantDTO> restaurantDTOs = restaurantService.getRestaurantByName(name);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, restaurantDTOs, restaurantDTOs.size());
    }   
}
    