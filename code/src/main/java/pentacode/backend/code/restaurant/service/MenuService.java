package pentacode.backend.code.restaurant.service;

import org.springframework.stereotype.Service;

import pentacode.backend.code.common.service.BaseService;
import pentacode.backend.code.restaurant.dto.MenuDTO;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.mapper.MenuMapper;
import pentacode.backend.code.restaurant.repository.MenuRepository;

@Service
public class MenuService extends BaseService<Menu> {
    private final MenuMapper menuMapper;
    private final MenuRepository menuRepository;

    public MenuService(MenuMapper menuMapper, MenuRepository menuRepository){
        super(menuRepository);
        this.menuMapper = menuMapper;
        this.menuRepository = menuRepository;
    }

    public MenuDTO getByPk(Long pk){
        return menuMapper.mapToDTO(super.findByPkOr404(pk));
    }
    
}
