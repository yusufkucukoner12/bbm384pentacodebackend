package pentacode.backend.code.common.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import pentacode.backend.code.common.dto.CreateOrderRequestDTO;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.dto.OrderItemRequestDTO;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.entity.OrderItem;
import pentacode.backend.code.common.entity.OrderStatusEnum;
import pentacode.backend.code.common.mapper.OrderMapper;
import pentacode.backend.code.common.repository.OrderItemRepository;
import pentacode.backend.code.common.repository.OrderRepository;
import pentacode.backend.code.common.service.base.BaseService;
import pentacode.backend.code.courier.entity.Courier;
import pentacode.backend.code.courier.repository.CourierRepository;
import pentacode.backend.code.customer.entity.Customer;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.repository.MenuRepository;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
public class OrderService extends BaseService<Order> {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final OrderItemRepository orderItemRepository;
    private final CourierRepository courierRepository;

    public OrderService(OrderRepository orderRepository, 
                        OrderMapper orderMapper,
                        RestaurantRepository restaurantRepository, 
                        MenuRepository menuRepository,
                        OrderItemRepository orderItemRepository,
                        CourierRepository courierRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.orderItemRepository = orderItemRepository;
        this.courierRepository = courierRepository;
    }

    public List<OrderDTO> getOrderByRestaurantPk(Long pk){
        return orderMapper.mapToListDTO(orderRepository.findByRestaurantPk(pk));
    }

    public OrderDTO getByPk(Long pk){
        return orderMapper.mapToDTO(super.findByPkOr404(pk));
    }
    public List<OrderDTO> getAllOrders() {
        return orderMapper.mapToListDTO(orderRepository.findAll());
    }
    @Transactional
    public OrderDTO placeOrder(CreateOrderRequestDTO request) {
        Restaurant restaurant = restaurantRepository.findById(request.getRestaurantId())
                .orElseThrow(() -> new RuntimeException("Restaurant not found"));

        Order order = new Order();
        order.setRestaurant(restaurant);
        order.setName("Order for " + restaurant.getName());

        List<OrderItem> orderItems = new ArrayList<>();

        for (OrderItemRequestDTO itemRequest : request.getItems()) {
            Menu menu = menuRepository.findById(itemRequest.getMenuId())
                    .orElseThrow(() -> new RuntimeException("Menu not found"));

            OrderItem orderItem = new OrderItem();
            orderItem.setMenu(menu);
            orderItem.setQuantity(itemRequest.getQuantity());
            orderItem.setOrder(order);
            orderItemRepository.save(orderItem);
            orderItems.add(orderItem);
        }

        order.setOrderItems(orderItems);

        for(OrderItem orderItem : orderItems) {
            order.setTotalPrice(order.getTotalPrice() + (orderItem.getMenu().getPrice() * orderItem.getQuantity()));
        }
        

        order = orderRepository.save(order);

        return orderMapper.mapToDTO(order);
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
    
    public OrderDTO courierAcceptAssignment(Long orderId, String status) {
        Order order = super.findByPkOr404(orderId);
        
        if (order == null || order.getCourier() == null) {
            return null;
        }
        
        if (status.equals("IN_TRANSIT")) {
            order.setCourierAssignmentAccepted(true);
            order.setStatus(OrderStatusEnum.IN_TRANSIT);
        } 
        else if (status.equals("DELIVERED")) {
            order.setStatus(OrderStatusEnum.DELIVERED);
        } 
        else if (status.equals("REJECTED")) {
            order.setStatus(OrderStatusEnum.REJECTED);
            order.setCourier(null);
            order.setCourierAssignmentAccepted(false);
            
        }
        else {
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

    public List<OrderDTO> getOrderByCourierPk(Long courierPk, boolean accept, boolean past) {
        if (past) {
            return orderMapper.mapToListDTO(orderRepository.findByCourierPkAndStatus(courierPk, OrderStatusEnum.DELIVERED));
        }
        if (accept) {
            return orderMapper.mapToListDTO(orderRepository.findByCourierPkAndStatus(courierPk, OrderStatusEnum.IN_TRANSIT));
        }
        else{
            return orderMapper.mapToListDTO(orderRepository.findByCourierPkAndCourierAssignmentAccepted(courierPk, false));
        }
    }
    @Transactional
    public OrderDTO updateOrderStatus(Long orderId, String status) {
        Order order = super.findByPkOr404(orderId);
        
        if (order == null) {
            return null;
        }
        
        try {
            OrderStatusEnum newStatus = OrderStatusEnum.valueOf(status);
            order.setStatus(newStatus);
            
            // Additional business logic based on the new status
            switch (newStatus) {
                case CANCELLED:
                case REJECTED:
                    // If order is cancelled or rejected, unassign any courier
                    if (order.getCourier() != null) {
                        order.setCourier(null);
                        order.setCourierAssignmentAccepted(false);
                    }
                    break;
                case READY_FOR_PICKUP:
                    break;
                default:
                    // No additional action for other statuses
                    break;
            }
            
            Order savedOrder = orderRepository.save(order);
            return orderMapper.mapToDTO(savedOrder);
            
        } catch (IllegalArgumentException e) {
            // Invalid status value
            return null;
        }
    }

    public Order createOrder(Customer customer) {
        Order order = new Order();
        customer.setOrder(order);
        return orderRepository.save(order);
    }

}
