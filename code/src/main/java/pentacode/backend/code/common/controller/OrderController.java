package pentacode.backend.code.common.controller;

import java.util.List;

import org.apache.catalina.connector.Response;
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

import lombok.AllArgsConstructor;
import pentacode.backend.code.auth.entity.User;
import pentacode.backend.code.common.dto.CreateOrderRequestDTO;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.common.utils.ResponseHandler;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    @GetMapping("{pk}")
    public ResponseEntity<Object> getOrderByPk(@PathVariable Long pk){
        return ResponseHandler.generatePkResponse("Success", HttpStatus.OK, orderService.getByPk(pk));
    }

    @GetMapping("restaurant/{pk}")
    public ResponseEntity<Object> getOrdersByRestaurantPk(@PathVariable Long pk){
        List<OrderDTO> orderDTOs = orderService.getOrderByRestaurantPk(pk);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, orderDTOs, orderDTOs.size()); 
    }
    @GetMapping("/all")
    public ResponseEntity<Object> getAllOrders() {
        List<OrderDTO> orderDTOs = orderService.getAllOrders();
        return ResponseHandler.generateListResponse("All orders fetched successfully", HttpStatus.OK, orderDTOs, orderDTOs.size());
    }
    @PostMapping("finish-order")
    public ResponseEntity<?> finishOrder(@RequestBody CreateOrderRequestDTO request) {
        try {
            OrderDTO order = orderService.placeOrder(request);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error");
        }
    }

    @GetMapping("/courier/orders")
    public ResponseEntity<Object> getOrdersByCourierPk(@AuthenticationPrincipal User user, @RequestParam("accept") boolean accept, @RequestParam("past") boolean past) {
        Long courierPk = user.getCourier().getPk();
        List<OrderDTO> orderDTOs = orderService.getOrderByCourierPk(courierPk, accept, past);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, orderDTOs, orderDTOs.size());
    }

    @PostMapping("/rate-order/{pk}")
    public ResponseEntity<Object> rateOrder(@PathVariable Long pk, @RequestParam("rating") Double rating) {
        System.out.println("ANANIZIN AMIII YAA");
        try {
            OrderDTO orderDTO = orderService.rateOrder(pk, rating);
            return ResponseEntity.ok("Success");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }

    @PostMapping("/re-order/{pk}")
    public ResponseEntity<Object> reOrder(@AuthenticationPrincipal User user, @PathVariable Long pk) {
        try {
            Order userOrder = user.getCustomer().getOrder();
            // now using the pk make it userOrder is same as the order with the pk
            OrderDTO orderDTO = orderService.reOrder(userOrder, pk);
            if (orderDTO != null) {
                return ResponseEntity.ok("Success");
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error");
        }
    }
}

