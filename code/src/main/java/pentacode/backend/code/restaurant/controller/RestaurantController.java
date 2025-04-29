package pentacode.backend.code.restaurant.controller;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pentacode.backend.code.auth.entity.User;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.service.RestaurantService;
import pentacode.backend.code.common.dto.UpdateOrderStatusRequestDTO;

@RestController
@RequestMapping("/api/restaurant")
@AllArgsConstructor
public class RestaurantController{
    private final RestaurantService restaurantService;
    private final OrderService orderService;

    @GetMapping("{pk}")
    public ResponseEntity<Object> getRestByPk(@AuthenticationPrincipal User user){
        Long pk = user.getRestaurant().getPk();
        return ResponseHandler.generatePkResponse("Success", HttpStatus.OK, restaurantService.getByPk(pk));
    }

    @GetMapping("name/{name}")
    public ResponseEntity<Object> getRestaurantByName(@PathVariable String name){
        List<RestaurantDTO> restaurantDTOs = restaurantService.getRestaurantByName(name);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, restaurantDTOs, restaurantDTOs.size());
    }   

    @GetMapping("/all")
    public ResponseEntity<Object> getAllRestaurants(){
        List<RestaurantDTO> restaurantDTOs = restaurantService.getAllRestaurants();
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, restaurantDTOs, restaurantDTOs.size());
    }
    
    @PostMapping("/orders/{orderId}/assign-courier/{courierId}")
    public ResponseEntity<Object> assignCourier(@AuthenticationPrincipal User user,
                                              @PathVariable("orderId") Long orderId,
                                              @PathVariable("courierId") Long courierId) {
        Long restaurantId = user.getRestaurant().getPk();
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
    @GetMapping("/get/orders")
    public ResponseEntity<Object> getOrdersByRestaurant(@AuthenticationPrincipal User user) {
        Long restaurantId = user.getRestaurant().getPk();
        List<OrderDTO> orderDTOs = restaurantService.getOrdersByRestaurant(restaurantId);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, orderDTOs, orderDTOs.size());
    }
    @PostMapping("/orders/{orderId}/status")
    public ResponseEntity<Object> updateOrderStatus(
            @AuthenticationPrincipal User user,
            @PathVariable("orderId") Long orderId,
            @RequestBody UpdateOrderStatusRequestDTO request) {
        Long restaurantId = user.getRestaurant().getPk();
        OrderDTO orderDTO = orderService.getByPk(orderId);
        if (orderDTO == null || !orderDTO.getRestaurant().getPk().equals(restaurantId)) {
            return ResponseHandler.generatePkResponse(
                "Invalid order or restaurant",
                HttpStatus.BAD_REQUEST,
                null
            );
        }
        
        // Update the order status
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, request.getStatus());
        if (updatedOrder == null) {
            return ResponseHandler.generatePkResponse(
                "Failed to update order status",
                HttpStatus.BAD_REQUEST,
                null
            );
        }
        
        return ResponseHandler.generatePkResponse(
            "Order status updated successfully",
            HttpStatus.OK,
            updatedOrder
        );
    }
}