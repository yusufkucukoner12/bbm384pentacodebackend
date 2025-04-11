package pentacode.backend.code.restaurant.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.service.RestaurantService;

@RestController
@RequestMapping("/api/restaurant")
@AllArgsConstructor
public class RestaurantController{
    private final RestaurantService restaurantService;
    private final OrderService orderService;

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
    
    @PostMapping("/{restaurantId}/orders/{orderId}/assign-courier/{courierId}")
    public ResponseEntity<Object> assignCourier(@PathVariable("restaurantId") Long restaurantId,
                                              @PathVariable("orderId") Long orderId,
                                              @PathVariable("courierId") Long courierId) {
        // First verify the restaurant owns this order
        OrderDTO orderDTO = orderService.getByPk(orderId);
        if (orderDTO == null || !orderDTO.getRestaurant().getPk().equals(restaurantId)) {
            return ResponseHandler.generatePkResponse("Invalid order or restaurant", 
                                                    HttpStatus.BAD_REQUEST, 
                                                    null);
        }
        
        OrderDTO updatedOrder = orderService.assignCourier(orderId, courierId);
        if (updatedOrder == null) {
            return ResponseHandler.generatePkResponse("Failed to assign courier", 
                                                    HttpStatus.BAD_REQUEST, 
                                                    null);
        }
        
        return ResponseHandler.generatePkResponse("Courier assigned successfully", 
                                                HttpStatus.OK, 
                                                updatedOrder);
    }
}