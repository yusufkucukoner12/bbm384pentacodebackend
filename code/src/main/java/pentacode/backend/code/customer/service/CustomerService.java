package pentacode.backend.code.customer.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.entity.OrderItem;
import pentacode.backend.code.common.entity.OrderStatusEnum;
import pentacode.backend.code.common.mapper.OrderMapper;
import pentacode.backend.code.common.repository.OrderItemRepository;
import pentacode.backend.code.common.repository.OrderRepository;
import pentacode.backend.code.common.service.base.BaseService;
import pentacode.backend.code.customer.entity.Customer;
import pentacode.backend.code.customer.repository.CustomerRepository;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
public class CustomerService extends BaseService<Customer>{
    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final RestaurantRepository restaurantRepository;


    public CustomerService(OrderRepository orderRepository, CustomerRepository customerRepository, OrderItemRepository orderItemRepository, OrderMapper orderMapper, RestaurantRepository restaurantRepository) {
        super(customerRepository);
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.orderItemRepository = orderItemRepository;
        this.orderMapper = orderMapper;
        this.restaurantRepository = restaurantRepository;
    }

    public void createOrder(Customer customer) {
        Order order = new Order();
        customer.setOrder(order);
        orderRepository.save(order);
        customerRepository.save(customer);
    }

    public void assignRestaurantToOrder(Order order, Restaurant restaurant) {
        order.setRestaurant(restaurant);
        orderRepository.save(order);
    }

    @jakarta.transaction.Transactional
    public void addMenuToOrder(Customer customer, Menu menu) {
        Order order = customer.getOrder();
        List<OrderItem> orderItems = order.getOrderItems();

        if (order.getRestaurant() == null) {
            order.setRestaurant(menu.getRestaurant());
        } else if (!order.getRestaurant().getPk().equals(menu.getRestaurant().getPk())) {
            throw new IllegalArgumentException("Menu does not belong to the restaurant of the current order.");
        }


        boolean menuExists = false;
        for (OrderItem item : orderItems) {
            if (item.getMenu().getPk().equals(menu.getPk())) {
                item.setQuantity(item.getQuantity() + 1);
                menuExists = true;
                break;
            }
        }
        if (!menuExists) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setMenu(menu);
            orderItem.setQuantity(1);
            orderItemRepository.save(orderItem);
        }
        order.setTotalPrice(order.getTotalPrice() + menu.getPrice());
        orderRepository.save(order);
        customerRepository.save(customer);
    }

    public void removeMenuFromOrder(Customer customer, Menu menu) {
        Order order = customer.getOrder();
        List<OrderItem> orderItem = order.getOrderItems();

        boolean itemFound = false;
        for (OrderItem item : orderItem) {
            System.out.println("Item: " + item.getMenu().getPk());
            if (item.getMenu().getPk().equals(menu.getPk())) {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    order.setTotalPrice(order.getTotalPrice() - menu.getPrice());
                } else {
                    item.setOrder(null);
                }
                orderItemRepository.save(item);
                itemFound = true;
            }
        }

        if (!itemFound) {
            throw new IllegalArgumentException("Menu not found in the order.");
        }

        orderRepository.save(order);
        customerRepository.save(customer);
    }

    public OrderDTO getCurrentOrder(Customer customer) {
        System.out.println("Customer order: " + customer.getOrder());
        return orderMapper.mapToDTO(customer.getOrder());
    }

    public void placeOrder(Customer customer) {
        Order order = customer.getOrder();
        if (order.getRestaurant() == null) {
            throw new IllegalArgumentException("Restaurant not assigned to the order.");
        }
        order.setStatus(OrderStatusEnum.PLACED);
        orderRepository.save(order);
    }
}
