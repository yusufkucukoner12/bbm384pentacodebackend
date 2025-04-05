package pentacode.backend.code.restaurant.mapper;


import java.util.List;

import org.mapstruct.Mapper;

import pentacode.backend.code.common.mapper.BaseMapper;
import pentacode.backend.code.restaurant.dto.FoodDTO;
import pentacode.backend.code.restaurant.dto.MenuDTO;
import pentacode.backend.code.restaurant.entity.Food;
import pentacode.backend.code.restaurant.entity.Menu;

@Mapper(componentModel="spring")
public interface MenuMapper extends BaseMapper<Menu, MenuDTO> {
    MenuDTO mapToDTO(Menu menu);
    Menu mapToEntity(MenuDTO menuDTO);
    List<MenuDTO> mapToListDTO(List<Menu> menus);
    List<Menu> mapToListEntity(List<MenuDTO> menuDTOs);
}
