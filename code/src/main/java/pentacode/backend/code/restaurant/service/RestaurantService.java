package pentacode.backend.code.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import pentacode.backend.code.common.service.base.BaseService;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Category;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.mapper.MenuMapper;
import pentacode.backend.code.restaurant.mapper.RestaurantMapper;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;
import pentacode.backend.code.common.dto.OrderDTO;
import pentacode.backend.code.common.mapper.OrderMapper;
import pentacode.backend.code.common.repository.OrderRepository;
import pentacode.backend.code.restaurant.repository.MenuRepository;
import pentacode.backend.code.restaurant.dto.MenuDTO;

@Service
public class RestaurantService extends BaseService<Restaurant> {
    private final RestaurantMapper restaurantMapper;
    private final RestaurantRepository restaurantRepository;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final MenuRepository menuRepository;
    private final MenuMapper menuMapper;
    
    public RestaurantService(
            RestaurantMapper restaurantMapper,
            @Qualifier("restaurantRepository") RestaurantRepository restaurantRepository,
            OrderRepository orderRepository,
            OrderMapper orderMapper,
            MenuRepository menuRepository,
            MenuMapper menuMapper) {
        super(restaurantRepository);
        this.restaurantMapper = restaurantMapper;
        this.restaurantRepository = restaurantRepository;
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
        this.menuRepository = menuRepository;
        this.menuMapper = menuMapper;
    }

    public RestaurantDTO getByPk(Long pk) {
        return restaurantMapper.mapToDTO(super.findByPkOr404(pk));
    }

    public Restaurant getRestaurantByPk(Long pk) {
        return restaurantRepository.findByPk(pk);
    }

    public RestaurantDTO getRestaurant(Long pk) {
        return restaurantMapper.mapToDTO(restaurantRepository.findByPk(pk));
    }

    public List<RestaurantDTO> getRestaurantByName(String name) {
        return restaurantMapper.mapToListDTO(restaurantRepository.findByName(name));
    }

    public List<RestaurantDTO> getAllRestaurants(String searchQuery, String sortOption) {
        try {
            Specification<Restaurant> spec = Specification.where(null);

            // Search by name or description
            if (searchQuery != null && !searchQuery.trim().isEmpty()) {
                String searchPattern = "%" + searchQuery.toLowerCase() + "%";
                spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("name")), searchPattern),
                    cb.like(cb.lower(root.get("description")), searchPattern)
                ));
            }

            List<Restaurant> restaurants = restaurantRepository.findAll(spec);

            // Sort in Java
            restaurants.sort((a, b) -> {
                if (sortOption == null || sortOption.equals("name-asc")) {
                    return a.getName().compareTo(b.getName());
                } else if (sortOption.equals("name-desc")) {
                    return b.getName().compareTo(a.getName());
                }
                return 0;
            });

            return restaurantMapper.mapToListDTO(restaurants);
        } catch (Exception e) {
            // Fallback to @Query method if Specification fails
            List<Restaurant> restaurants = restaurantRepository.findRestaurantsWithFilters(
                searchQuery != null && !searchQuery.trim().isEmpty() ? searchQuery : null,
                sortOption != null ? sortOption : "name-asc"
            );
            return restaurantMapper.mapToListDTO(restaurants);
        }
    }

    public List<OrderDTO> getOrdersByRestaurant(Long restaurantId) {
        return orderMapper.mapToListDTO(orderRepository.findByRestaurantPk(restaurantId));
    }

    public List<MenuDTO> getMenusByRestaurantAndCategory(Long restaurantId, String categoryName) {
        List<Menu> menus = menuRepository.findByRestaurantPkAndCategoryName(restaurantId, categoryName);
        return menuMapper.mapToListDTO(menus);
    }

    public List<RestaurantDTO> listAllRestaurants() {
        List<Restaurant> restaurants = restaurantRepository.findAll();
        return restaurantMapper.mapToListDTO(restaurants);
    }
}