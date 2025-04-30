package pentacode.backend.code.restaurant.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import pentacode.backend.code.auth.entity.User;
import pentacode.backend.code.common.utils.ResponseHandler;
import pentacode.backend.code.restaurant.dto.MenuDTO;
import pentacode.backend.code.restaurant.service.MenuService;

@RestController
@RequestMapping("/api/menu")
@AllArgsConstructor
public class MenuController {
    private final MenuService menuService;

    @GetMapping("{pk}")
    public ResponseEntity<Object> getMenuByPk(@PathVariable Long pk) {
        return ResponseHandler.generatePkResponse("Success", HttpStatus.OK, menuService.getByPk(pk));
    }

    @GetMapping("restaurant")
    public ResponseEntity<Object> getMenuByRestaurant(@AuthenticationPrincipal User user) {
        System.out.println("User: " + user.getUsername());
        System.out.println("Restaurant: " + user.getRestaurant().getName());
        System.out.println("Restaurant PK: " + user.getRestaurant().getPk());
        Long restaurantPk = user.getRestaurant().getPk();
        List<MenuDTO> menuDTOs = menuService.getMenuByRestaurant(restaurantPk);
        return ResponseHandler.generateListResponse("Success", HttpStatus.OK, menuDTOs, menuDTOs.size());
    }

    @PostMapping
    public ResponseEntity<Object> createMenu(
            @Valid @RequestBody MenuDTO menuDTO,
            @AuthenticationPrincipal User user) {
                System.out.println("Received MenuDTO: " + menuDTO);
                System.out.println("Received isDrink: " + menuDTO.isDrink());
                System.out.println("Received isAvailable: " + menuDTO.isAvailable());
        MenuDTO createdMenu = menuService.createMenu(menuDTO, user.getRestaurant().getPk());
        System.out.println("Created Menu: " + createdMenu);
        System.out.println("Created Menu PK: " + createdMenu.getPk());
        System.out.println("Created Menu Name: " + createdMenu.getName());
        System.out.println("Created Menu Description: " + createdMenu.getDescription());
        System.out.println("Created Menu Price: " + createdMenu.getPrice());
        System.out.println("Created Menu Image URL: " + createdMenu.getImageUrl());
        System.out.println("Created Menu Is Available: " + createdMenu.isAvailable());
        System.out.println("Created Menu Is Drink: " + createdMenu.isDrink());
        return ResponseHandler.generatePkResponse("Menu created successfully", HttpStatus.CREATED, createdMenu);
    }

    @PutMapping("{pk}")
    public ResponseEntity<Object> updateMenu(
            @PathVariable Long pk,
            @Valid @RequestBody MenuDTO menuDTO,
            @AuthenticationPrincipal User user) {
        MenuDTO updatedMenu = menuService.updateMenu(pk, menuDTO, user.getRestaurant().getPk());
        return ResponseHandler.generatePkResponse("Menu updated successfully", HttpStatus.OK, updatedMenu);
    }

    @DeleteMapping("{pk}")
    public ResponseEntity<Object> deleteMenu(
            @PathVariable Long pk,
            @AuthenticationPrincipal User user) {
        menuService.deleteMenu(pk, user.getRestaurant().getPk());
        return ResponseHandler.generatePkResponse("Menu deleted successfully", HttpStatus.OK,null);
    }
}