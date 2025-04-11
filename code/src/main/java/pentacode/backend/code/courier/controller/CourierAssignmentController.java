package pentacode.backend.code.courier.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.common.utils.ResponseHandler;

@RestController
@RequestMapping("/api/couriers")
public class CourierAssignmentController {
    private final OrderService orderService;
    
    public CourierAssignmentController(OrderService orderService) {
        this.orderService = orderService;
    }
    
    @PostMapping("/{courierId}/orders/{orderId}/respond")
    public ResponseEntity<Object> respondToAssignment(@PathVariable("courierId") Long courierId,
                                                     @PathVariable("orderId") Long orderId,
                                                     @RequestParam("accept") boolean accept) {
        OrderDTO order = orderService.getByPk(orderId);
        
        if (order == null || order.getCourier() == null || !order.getCourier().getPk().equals(courierId)) {
            return ResponseHandler.generatePkResponse("Invalid order or courier", 
                                                    HttpStatus.BAD_REQUEST, 
                                                    null);
        }
        
        OrderDTO updatedOrder = orderService.courierAcceptAssignment(orderId, accept);
        
        if (accept) {
            return ResponseHandler.generatePkResponse("Assignment accepted successfully", 
                                                    HttpStatus.OK, 
                                                    updatedOrder);
        } else {
            return ResponseHandler.generatePkResponse("Assignment rejected", 
                                                    HttpStatus.OK, 
                                                    updatedOrder);
        }
    }
}