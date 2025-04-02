package pentacode.backend.code.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import pentacode.backend.code.common.controller.BaseController;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.OrderDTO;
import pentacode.backend.code.restaurant.entity.Order;
import pentacode.backend.code.restaurant.mapper.OrderMapper;
import pentacode.backend.code.restaurant.service.OrderService;

@RestController
@RequestMapping("/api/order")
public class OrderController extends BaseController<Order, OrderMapper> {
    private final OrderService orderService;
    private final OrderMapper orderMapper;

    public OrderController(OrderService orderService, OrderMapper orderMapper){
        super(orderService, orderMapper);
        this.orderService = orderService;
        this.orderMapper = orderMapper;
    }

    @GetMapping("{pk}")
    public ResponseEntity<Object> getOrderByPk(@PathVariable Long pk){
        return super.getByPkOr404(pk);
    } 

    @GetMapping("restaurant/{pk}")
    public ResponseEntity<Object> getOrdersByRestaurantPk(@PathVariable Long pk){
        List<OrderDTO> orderDTOs = orderService.getOrderByRestaurantPk(pk);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, orderDTOs, orderDTOs.size()); 
    }
}
