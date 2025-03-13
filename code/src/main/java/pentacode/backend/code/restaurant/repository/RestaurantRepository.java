package pentacode.backend.code.restaurant.repository;
import java.util.List;

import org.springframework.context.annotation.Primary;

import pentacode.backend.code.common.repository.BaseRepository;
import pentacode.backend.code.restaurant.entity.Restaurant;

@Primary
public interface RestaurantRepository extends BaseRepository<Restaurant> {
    List<Restaurant> findByName(String name);
}
