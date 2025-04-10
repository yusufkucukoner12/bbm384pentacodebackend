package pentacode.backend.code.restaurant.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
@AllArgsConstructor
public class RestaurantController{
    private final RestaurantService restaurantService;

    @GetMapping("{pk}")
    public ResponseEntity<Object> getRestByPk(@PathVariable Long pk){
        return ResponseHandler.generatePkResponse("Success", HttpStatus.OK, restaurantService.getByPk(pk));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Object> getRestaurantByName(@PathVariable String name){
        List<RestaurantDTO> restaurantDTOs = restaurantService.getRestaurantByName(name);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, restaurantDTOs, restaurantDTOs.size());
    }   

    @GetMapping("all")
    public ResponseEntity<Object> getAllRestaurants(){
        List<RestaurantDTO> restaurantDTOs = restaurantService.getAllRestaurants();
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, restaurantDTOs, restaurantDTOs.size());
    }
}    