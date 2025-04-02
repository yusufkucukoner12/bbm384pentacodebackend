package pentacode.backend.code.restaurant.repository;
import java.util.List;

import pentacode.backend.code.common.repository.BaseRepository;
import pentacode.backend.code.restaurant.entity.Restaurant;

public interface RestaurantRepository extends BaseRepository<Restaurant> {
    List<Restaurant> findByName(String name);
}
