package pentacode.backend.code.common.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.restaurant.dto.CustomerDTO;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.dto.DeliveryDTO;
import pentacode.backend.code.restaurant.dto.MenuDTO;

@Getter
@Setter
@Builder
public class OrderDTO {
    private Long id;
    private String name;
    private String orderStatus;
    private Boolean isCart;
    private Integer orderCost;
    private CustomerDTO customer;
    private RestaurantDTO restaurant;
    private DeliveryDTO delivery;
    private List<MenuDTO> menus;
}