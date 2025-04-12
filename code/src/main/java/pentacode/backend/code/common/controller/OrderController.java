package pentacode.backend.code.common.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pentacode.backend.code.common.dto.CreateOrderRequestDTO;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.service.OrderService;
import pentacode.backend.code.common.utils.ResponseHandler;

@RestController
@RequestMapping("/api/order")
@AllArgsConstructor
public class OrderController {
    private final OrderService orderService;

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

    @GetMapping("/courier/{courierPk}")
    public ResponseEntity<Object> getOrdersByCourierPk(@PathVariable Long courierPk, @RequestParam("accept") boolean accept) {
        List<OrderDTO> orderDTOs = orderService.getOrderByCourierPk(courierPk, accept);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, orderDTOs, orderDTOs.size());
    }
}
