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
import pentacode.backend.code.common.mapper.OrderMapper;
import pentacode.backend.code.common.repository.OrderItemRepository;
import pentacode.backend.code.common.repository.OrderRepository;
import pentacode.backend.code.common.service.base.BaseService;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.repository.MenuRepository;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
public class OrderService extends BaseService<Order>{
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;
    private final OrderItemRepository orderItemRepository;

    public OrderService(OrderRepository orderRepository, 
                        OrderMapper orderMapper,
                        RestaurantRepository restaurantRepository, 
                        MenuRepository menuRepository,
                        OrderItemRepository orderItemRepository) {
        super(orderRepository);
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
        this.menuRepository = menuRepository;
        this.orderItemRepository = orderItemRepository;
    }

    public List<OrderDTO> getOrderByRestaurantPk(Long pk){
        return orderMapper.mapToListDTO(orderRepository.findByRestaurantPk(pk));
    }

    public OrderDTO getByPk(Long pk){
        return orderMapper.mapToDTO(super.findByPkOr404(pk));
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

        order = orderRepository.save(order);

        return orderMapper.mapToDTO(order);
    }
}
