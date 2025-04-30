package pentacode.backend.code.courier.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import pentacode.backend.code.auth.entity.User;
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
    
    @PostMapping("/orders/{orderId}/respond")
    public ResponseEntity<Object> respondToAssignment(@AuthenticationPrincipal User user,
                                                     @PathVariable("orderId") Long orderId,
                                                     @RequestParam("status") String status) {
        Long courierId = user.getCourier().getPk();
        OrderDTO order = orderService.getByPk(orderId);
        
        if (order == null || order.getCourier() == null || !order.getCourier().getPk().equals(courierId)) {
            return ResponseHandler.generatePkResponse("Invalid order or courier", 
                                                    HttpStatus.BAD_REQUEST, 
                                                    null);
        }
        
        OrderDTO updatedOrder = orderService.courierAcceptAssignment(orderId, status);
        
        if (true) {
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