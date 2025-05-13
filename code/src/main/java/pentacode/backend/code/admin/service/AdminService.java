package pentacode.backend.code.admin.service;

import java.util.List;

import org.springframework.stereotype.Service;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.courier.dto.CourierDTO;
import pentacode.backend.code.customer.dto.CustomerDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.restaurant.service.RestaurantService;
import pentacode.backend.code.courier.service.CourierService;
import pentacode.backend.code.customer.service.CustomerService;

@Service
public class AdminService {
    private final OrderService orderService;
    private final CourierService courierService;
    private final RestaurantService restaurantService;
    private final CustomerService customerService;

    public AdminService(OrderService orderService,
                        CourierService courierService,
                        RestaurantService restaurantService,
                        CustomerService customerService) {
        this.customerService = customerService;
        this.orderService = orderService;
        this.courierService = courierService;
        this.restaurantService = restaurantService;
    }

    public OrderDTO assignCourier(Long orderId, Long courierId) {
        return orderService.assignCourier(orderId, courierId);
    }

    public OrderDTO unassignCourier(Long orderId) {
        return orderService.unassignCourier(orderId);
    }

    public List<OrderDTO> getAllOrders() {
        return orderService.getAllOrders();
    }

    public List<CourierDTO> getAllCouriers() {
        return courierService.getAllCouriers();
    }

    public List<RestaurantDTO> getAllRestaurants() {
        return restaurantService.listAllRestaurants();
    }

    public List<CustomerDTO> getAllCustomers() {
        return customerService.listAllCustomers();
    }
}
