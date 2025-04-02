package pentacode.backend.code.restaurant.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import pentacode.backend.code.common.service.BaseService;
import pentacode.backend.code.restaurant.dto.OrderDTO;
import pentacode.backend.code.restaurant.entity.Order;
import pentacode.backend.code.restaurant.mapper.OrderMapper;
import pentacode.backend.code.restaurant.repository.OrderRepository;

@Service
public class OrderService extends BaseService<Order>{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(@Qualifier("orderRepository") OrderRepository orderRepository, OrderMapper orderMapper) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDTO> getOrderByRestaurantPk(Long pk){
        return orderMapper.mapToListDTO(orderRepository.findByRestaurantPk(pk));
    }
    
}
