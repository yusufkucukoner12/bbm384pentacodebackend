package pentacode.backend.code.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.MenuDTO;
import pentacode.backend.code.restaurant.service.MenuService;

@RestController
@RequestMapping("/api/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;


    @GetMapping("{pk}")
    public ResponseEntity<Object> getMenuByPk(@PathVariable Long pk){
        return ResponseHandler.generatePkResponse("Success", HttpStatus.OK, menuService.getByPk(pk));
    }

    @GetMapping("restaurant/{restaurantPk}")
    public ResponseEntity<Object> getMenuByRestaurant(@PathVariable Long restaurantPk){
        List<MenuDTO> menuDTOs = menuService.getMenuByRestaurant(restaurantPk);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, menuDTOs, menuDTOs.size());
    }
}

