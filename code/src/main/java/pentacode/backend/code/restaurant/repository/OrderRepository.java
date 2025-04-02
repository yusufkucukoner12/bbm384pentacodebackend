package pentacode.backend.code.restaurant.repository;

import java.util.List;

import pentacode.backend.code.common.repository.BaseRepository;
import pentacode.backend.code.restaurant.entity.Order;

public interface OrderRepository extends BaseRepository<Order>{
    List<Order> findByRestaurantPk(Long restaurantId);

}
