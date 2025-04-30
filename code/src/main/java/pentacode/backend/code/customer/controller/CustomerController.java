package pentacode.backend.code.customer.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.criteria.Order;
import pentacode.backend.code.auth.entity.User;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.customer.entity.Customer;
import pentacode.backend.code.customer.service.CustomerService;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.service.MenuService;

@RestController
@RequestMapping("/api/customer")
public class CustomerController {
    private final CustomerService customerService;
    private final MenuService menuService;

    private CustomerController(CustomerService customerService, MenuService menuService) {
        this.customerService = customerService;
        this.menuService = menuService;
    }
    

    @PostMapping("/create-order")
    public ResponseEntity<Object> createOrder(@AuthenticationPrincipal User user) {
        try {
            Customer customer = user.getCustomer();
            customerService.createOrder(customer);
            return ResponseHandler.generatePkResponse("Order created successfully", HttpStatus.OK, null);
        }   
        catch (Exception e) {
            return ResponseHandler.generatePkResponse("Error creating order", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }        
    }
    
    @PostMapping("/update-order/{pk}")
    public ResponseEntity<Object> updateOrder(@AuthenticationPrincipal User user, @PathVariable Long pk, @RequestParam String action) {
        try {
            Menu menu = menuService.getMenu(pk);
            Customer customer = user.getCustomer();
            System.out.println("Customer: " + customer);
            System.out.println("Menu: " + menu);
            if(action.equals("add")) {
                customerService.addMenuToOrder(customer, menu);
            } else if(action.equals("remove")) {
                customerService.removeMenuFromOrder(customer, menu);
            }
            return ResponseHandler.generatePkResponse("Order updated successfully", HttpStatus.OK, "Başarılı");
        }   
        catch (Exception e) {
            return ResponseHandler.generatePkResponse(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR, "Başarısız");
        }        
    }

    @GetMapping("/get-order")
    public ResponseEntity<Object> getOrder(@AuthenticationPrincipal User user) {
        try {
            Customer customer = user.getCustomer();
            OrderDTO order = customerService.getCurrentOrder(customer);
            System.out.println("Order: " + order);
            System.out.println("Customer: " + customer);
            return ResponseHandler.generatePkResponse("Order retrieved successfully", HttpStatus.OK, order);
        }   
        catch (Exception e) {
            return ResponseHandler.generatePkResponse("Error retrieving order", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }        
    }

    @PostMapping("place-order")
    public ResponseEntity<Object> placeOrder(@AuthenticationPrincipal User user) {
        try {
            Customer customer = user.getCustomer();
            customerService.placeOrder(customer);
            return ResponseHandler.generatePkResponse("Order placed successfully", HttpStatus.OK, null);
        }   
        catch (Exception e) {
            return ResponseHandler.generatePkResponse("Error placing order", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }        
    }    
}
