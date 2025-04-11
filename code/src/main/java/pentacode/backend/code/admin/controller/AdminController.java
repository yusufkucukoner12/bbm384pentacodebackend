package pentacode.backend.code.admin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.common.utils.ResponseHandler;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final OrderService orderService;
    
    public AdminController(OrderService orderService) {
        this.orderService = orderService;
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
}