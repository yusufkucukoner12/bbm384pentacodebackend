package pentacode.backend.code.common.repository;

import java.util.List;

import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.repository.base.BaseRepository;

public interface OrderRepository extends BaseRepository<Order>{
    List<Order> findByRestaurantPk(Long restaurantId);
    List<Order> findByCourierPk(Long courierId);
    List<Order> findByCourierPkAndCourierAssignmentAccepted(Long courierId, boolean accept);
}
