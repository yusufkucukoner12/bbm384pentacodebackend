package pentacode.backend.code.admin.controller;

import java.util.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.courier.dto.CourierDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.restaurant.service.RestaurantService;
import pentacode.backend.code.courier.service.CourierService;
import pentacode.backend.code.customer.service.CustomerService;
import pentacode.backend.code.customer.dto.CustomerDTO;
import pentacode.backend.code.common.utils.ResponseHandler;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final OrderService orderService;
    private final CourierService courierService;
    private final RestaurantService restaurantService;
    private final CustomerService customerService;

    
    public AdminController(OrderService orderService,
                           CourierService courierService,
                           RestaurantService restaurantService,
                           CustomerService customerService) {
        this.orderService = orderService;
        this.courierService = courierService;
        this.restaurantService = restaurantService;
        this.customerService = customerService;
    }
    
    @PostMapping("/orders/{orderId}/assign-courier/{courierId}")
    public ResponseEntity<Object> assignCourier(@PathVariable("orderId") Long orderId,
                                               @PathVariable("courierId") Long courierId) {
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
    @PostMapping("/orders/{orderId}/unassign-courier")
    public ResponseEntity<Object> unassignCourier(@PathVariable("orderId") Long orderId) {
        OrderDTO updatedOrder = orderService.unassignCourier(orderId);
        if (updatedOrder == null) {
            return ResponseHandler.generatePkResponse("Failed to unassign courier", 
                                                    HttpStatus.BAD_REQUEST, 
                                                    null);
        }
        
        return ResponseHandler.generatePkResponse("Courier unassigned successfully", 
                                                HttpStatus.OK, 
                                                updatedOrder);
    }
    
    @GetMapping("/order/all")
    public ResponseEntity<Object> getAllOrders() {
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseHandler.generatePkResponse("Fetched orders", HttpStatus.OK, orders);
    }

    @GetMapping("/courier/all")
    public ResponseEntity<Object> getAllCouriers() {
        List<CourierDTO> couriers = courierService.getAllCouriers();
        return ResponseHandler.generatePkResponse("Fetched couriers", HttpStatus.OK, couriers);
    }
    
    @GetMapping("/restaurant/all")
    public ResponseEntity<Object> listAllRestaurants() {
        List<RestaurantDTO> restaurants = restaurantService.listAllRestaurants();
        return ResponseHandler.generatePkResponse("Fetched restaurants", HttpStatus.OK, restaurants);
    }
    @GetMapping("/customer/all")
    public ResponseEntity<Object> listAllCustomers() {
        List<CustomerDTO> customers = customerService.listAllCustomers();
        return ResponseHandler.generatePkResponse("Fetched customers", HttpStatus.OK, customers);
}


}