package pentacode.backend.code.common.service;

import java.util.List;

import org.springframework.stereotype.Service;

import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.mapper.OrderMapper;
import pentacode.backend.code.common.repository.OrderRepository;
import pentacode.backend.code.common.service.base.BaseService;

@Service
public class OrderService extends BaseService<Order>{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    public List<OrderDTO> getOrderByRestaurantPk(Long pk){
        return orderMapper.mapToListDTO(orderRepository.findByRestaurantPk(pk));
    }

    public OrderDTO getByPk(Long pk){
        return orderMapper.mapToDTO(super.findByPkOr404(pk));
    }
    

}
