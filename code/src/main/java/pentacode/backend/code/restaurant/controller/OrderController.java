package pentacode.backend.code.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.OrderDTO;
import pentacode.backend.code.restaurant.service.OrderService;

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
}
