package pentacode.backend.code.restaurant.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import pentacode.backend.code.common.service.base.BaseService;
import pentacode.backend.code.restaurant.dto.MenuDTO;
import pentacode.backend.code.restaurant.entity.Category;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.mapper.MenuMapper;
import pentacode.backend.code.restaurant.repository.MenuRepository;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
public class MenuService extends BaseService<Menu> {
    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;
    private final RestaurantRepository restaurantRepository;

    public MenuService(MenuMapper menuMapper, MenuRepository menuRepository, RestaurantRepository restaurantRepository) {
        super(menuRepository);
        this.menuMapper = menuMapper;
        this.menuRepository = menuRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public MenuDTO getByPk(Long pk) {
        return menuMapper.mapToDTO(super.findByPkOr404(pk));
    }

    public MenuDTO getMenu(Long pk) {
        return menuMapper.mapToDTO(menuRepository.findByPk(pk));
    }
    
    public List<MenuDTO> getMenuByRestaurant(Long restaurantPk) {
        return menuMapper.mapToListDTO(menuRepository.findByRestaurantPk(restaurantPk));
    }

    @Transactional
    public MenuDTO createMenu(MenuDTO menuDTO, Long restaurantPk) {
        Restaurant restaurant = restaurantRepository.findById(restaurantPk)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found"));
        
        Menu menu = menuMapper.mapToEntity(menuDTO);
        menu.setRestaurant(restaurant);
        
        // Handle categories
        if (menuDTO.getCategoryPks() != null && !menuDTO.getCategoryPks().isEmpty()) {
            List<Category> categories = restaurant.getCategories().stream()
                    .filter(c -> menuDTO.getCategoryPks().contains(c.getPk()))
                    .collect(Collectors.toList());
            if (categories.size() != menuDTO.getCategoryPks().size()) {
                throw new IllegalArgumentException("One or more categories not found for this restaurant");
            }
            menu.setCategories(categories);
        } else {
            menu.setCategories(new ArrayList<>());
        }
        
        menu = menuRepository.save(menu);
        return menuMapper.mapToDTO(menu);
    }

    @Transactional
    public MenuDTO updateMenu(Long pk, MenuDTO menuDTO, Long restaurantPk) {
        Menu menu = menuRepository.findById(pk)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
        if (!menu.getRestaurant().getPk().equals(restaurantPk)) {
            throw new IllegalAccessError("Menu does not belong to this restaurant");
        }
        
        menu.setName(menuDTO.getName());
        menu.setDescription(menuDTO.getDescription());
        menu.setPrice(menuDTO.getPrice());
        menu.setImageUrl(menuDTO.getImageUrl());
        menu.setAvailable(menuDTO.isAvailable());
        menu.setDrink(menuDTO.isDrink());
        menu.setCategory(menuDTO.getCategory());
        
        // Handle categories
        if (menuDTO.getCategoryPks() != null) {
            Restaurant restaurant = menu.getRestaurant();
            List<Category> categories = restaurant.getCategories().stream()
                    .filter(c -> menuDTO.getCategoryPks().contains(c.getPk()))
                    .collect(Collectors.toList());
            if (categories.size() != menuDTO.getCategoryPks().size()) {
                throw new IllegalArgumentException("One or more categories not found for this restaurant");
            }
            menu.setCategories(categories);
        } else {
            menu.setCategories(new ArrayList<>());
        }
        
        menu = menuRepository.save(menu);
        return menuMapper.mapToDTO(menu);
    }

    @Transactional
    public void deleteMenu(Long pk, Long restaurantPk) {
        Menu menu = menuRepository.findById(pk)
                .orElseThrow(() -> new IllegalArgumentException("Menu not found"));
        if (!menu.getRestaurant().getPk().equals(restaurantPk)) {
            throw new IllegalAccessError("Menu does not belong to this restaurant");
        }
        menuRepository.delete(menu);
    }
}