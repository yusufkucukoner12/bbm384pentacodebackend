package pentacode.backend.code.restaurant.repository;
import java.util.List;

import pentacode.backend.code.common.repository.base.BaseRepository;
import pentacode.backend.code.restaurant.entity.Restaurant;

public interface RestaurantRepository extends BaseRepository<Restaurant> {
    List<Restaurant> findByName(String name);
    List<Restaurant> findAll();
    Restaurant findByPk(Long pk);
}
