package pentacode.backend.code.common.service;

import java.util.List;
import org.springframework.stereotype.Service;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.entity.OrderStatusEnum;
import pentacode.backend.code.common.mapper.OrderMapper;
import pentacode.backend.code.common.repository.OrderRepository;
import pentacode.backend.code.common.service.base.BaseService;
import pentacode.backend.code.courier.entity.Courier;
import pentacode.backend.code.courier.repository.CourierRepository;

@Service
public class OrderService extends BaseService<Order> {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final CourierRepository courierRepository;
    
    public OrderService(OrderRepository orderRepository, OrderMapper orderMapper, CourierRepository courierRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.courierRepository = courierRepository;
    }
    
    public List<OrderDTO> getOrderByRestaurantPk(Long pk) {
        return orderMapper.mapToListDTO(orderRepository.findByRestaurantPk(pk));
    }
    
    public OrderDTO getByPk(Long pk) {
        return orderMapper.mapToDTO(super.findByPkOr404(pk));
    }
    public List<OrderDTO> getAllOrders() {
        return orderMapper.mapToListDTO(orderRepository.findAll());
    }
    public OrderDTO assignCourier(Long orderId, Long courierId) {
        Order order = super.findByPkOr404(orderId);
        Courier courier = courierRepository.findByPk(courierId);
        
        if (order == null || courier == null) {
            return null;
        }
        
        // Check if order status allows reassignment
        if (order.getStatus() == OrderStatusEnum.DELIVERED || 
            order.getStatus() == OrderStatusEnum.CANCELLED || 
            order.getStatus() == OrderStatusEnum.REJECTED) {
            return null;
        }
        
        // Check if courier is available and online
        if (!courier.isAvailable() || !courier.isOnline()) {
            return null;
        }
        
        order.setCourier(courier);
        order.setStatus(OrderStatusEnum.ASSIGNED);
        order.setCourierAssignmentAccepted(false);
        
        // Save the updated order
        Order savedOrder = orderRepository.save(order);
        
        // Here we would normally notify the courier about the new assignment
        // This would be implemented with a messaging system or push notifications
        
        return orderMapper.mapToDTO(savedOrder);
    }
    
    public OrderDTO courierAcceptAssignment(Long orderId, boolean accepted) {
        Order order = super.findByPkOr404(orderId);
        
        if (order == null || order.getCourier() == null) {
            return null;
        }
        
        if (accepted) {
            order.setCourierAssignmentAccepted(true);
            order.setStatus(OrderStatusEnum.IN_TRANSIT);
        } else {
            // Reset the courier assignment
            order.setCourier(null);
            order.setCourierAssignmentAccepted(false);
            order.setStatus(OrderStatusEnum.READY_FOR_PICKUP);
        }
        
        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapToDTO(savedOrder);
    }
    public OrderDTO unassignCourier(Long orderId) {
        Order order = orderRepository.findById(orderId).orElse(null);

        if (order == null || order.getCourier() == null) {
            return null;
        }

        order.setCourier(null);
        order.setCourierAssignmentAccepted(false);
        order.setStatus(OrderStatusEnum.READY_FOR_PICKUP);

        Order savedOrder = orderRepository.save(order);
        return orderMapper.mapToDTO(savedOrder);
    }
}