package pentacode.backend.code.restaurant.repository;

import java.util.List;

import pentacode.backend.code.common.repository.base.BaseRepository;
import pentacode.backend.code.restaurant.entity.Menu;


public interface MenuRepository extends BaseRepository<Menu> {
    List<Menu> findByRestaurantPk(Long restaurantPk);
    List<Menu> findByRestaurantPkAndCategoryName(Long restaurantPk, String categoryName);
}