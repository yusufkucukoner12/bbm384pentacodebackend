package pentacode.backend.code.admin.controller;

import java.util.*;

import org.apache.catalina.connector.Request;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties.Admin;
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
import pentacode.backend.code.admin.service.AdminService;
import pentacode.backend.code.auth.CreateUserRequest;
import pentacode.backend.code.auth.CreateUserResponse;
import pentacode.backend.code.auth.entity.User;
import pentacode.backend.code.customer.dto.CustomerDTO;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.auth.service.AuthenticationService;
import pentacode.backend.code.common.dto.UpdateOrderStatusRequestDTO;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final OrderService orderService;
    private final CourierService courierService;
    private final RestaurantService restaurantService;
    private final CustomerService customerService;
    private final AdminService adminService;
    private final AuthenticationService authenticationService;

    
    public AdminController(OrderService orderService,
                           CourierService courierService,
                           RestaurantService restaurantService,
                           CustomerService customerService,
                           AdminService adminService,
                           AuthenticationService authenticationService) {
        this.adminService = adminService;
        this.orderService = orderService;
        this.courierService = courierService;
        this.restaurantService = restaurantService;
        this.customerService = customerService;
        this.authenticationService = authenticationService;
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
    @GetMapping("/{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable Long userId) {
        User user = adminService.getUserById(userId);
        return ResponseHandler.generatePkResponse("Fetched the customer", HttpStatus.OK, user);
    }
    @PutMapping("/ban/{userId}")
    public ResponseEntity<Object> banUser(@PathVariable Long userId) {
        Optional<User> user = adminService.banUser(userId);
        return ResponseHandler.generatePkResponse("User banned successfully", HttpStatus.OK, user);
    }
    @PutMapping("/unban/{userId}")
    public ResponseEntity<Object> unbanUser(@PathVariable Long userId) {
        Optional<User> user = adminService.unbanUser(userId);
        return ResponseHandler.generatePkResponse("User unbanned", HttpStatus.OK, user);
    }
    @GetMapping("/getban/{userId}")
    public ResponseEntity<Object> getUserBanStatus(@PathVariable Long userId) {
        Boolean isBanned = adminService.getUserBanStatus(userId);
        return ResponseHandler.generatePkResponse("User ban status retrieved" , HttpStatus.OK , isBanned);
    }
    @PostMapping("/create")
    public ResponseEntity<Object> createUserByAdmin(@RequestBody CreateUserRequest request) {
        CreateUserResponse response = authenticationService.createUser(request);
        if (response.getMessage().equals("Success")) {
            return ResponseHandler.generatePkResponse("User created successfully", HttpStatus.CREATED, response.getUser());
        } else {
            return ResponseHandler.generatePkResponse(response.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }
<<<<<<< HEAD
    @PutMapping("/orders/{orderId}/status")
    public ResponseEntity<Object> updateOrderStatus(
            @PathVariable("orderId") Long orderId,
            @RequestBody UpdateOrderStatusRequestDTO request) {
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
=======

    @PutMapping("/customer/edit/{pk}")
    public ResponseEntity<Object> updateCustomerProfile(@PathVariable Long pk, @RequestBody CustomerDTO dto) {
        CustomerDTO updatedCustomer = adminService.updateCustomerProfile(pk, dto);
        if (updatedCustomer == null) {
            return ResponseHandler.generatePkResponse("Failed to update customer profile", HttpStatus.BAD_REQUEST, null);
        }
        return ResponseHandler.generatePkResponse("Customer profile updated successfully", HttpStatus.OK, updatedCustomer);
    }

    @PutMapping("/restaurant/edit/{pk}")
    public ResponseEntity<Object> updateRestaurantProfile(@PathVariable Long pk, @RequestBody RestaurantDTO dto) {
        RestaurantDTO restaurant = adminService.updateRestaurantProfile(pk, dto);
        if (restaurant == null) {
            return ResponseHandler.generatePkResponse("Failed to fetch restaurant profile", HttpStatus.BAD_REQUEST, null);
        }
        return ResponseHandler.generatePkResponse("Restaurant profile fetched successfully", HttpStatus.OK, restaurant);
    }

    @PutMapping("/courier/edit/{pk}")
    public ResponseEntity<Object> updateCourierProfile(@PathVariable Long pk, @RequestBody CourierDTO dto) {
        CourierDTO courier = adminService.updateCourierProfile(pk, dto);
        if (courier == null) {
            return ResponseHandler.generatePkResponse("Failed to fetch courier profile", HttpStatus.BAD_REQUEST, null);
        }
        return ResponseHandler.generatePkResponse("Courier profile fetched successfully", HttpStatus.OK, courier);
>>>>>>> 43303daa9e1b4e2d1e0cf7c9d83cac0d0fcf6aca
    }
}