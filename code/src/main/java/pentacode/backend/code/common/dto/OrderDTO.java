package pentacode.backend.code.common.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.restaurant.dto.MenuDTO;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;

@Getter
@Setter
@Builder
public class OrderDTO {
    public Long id;
    public String name;
    private String orderStatus;
    private CustomerDTO customer;
    public RestaurantDTO restaurant;
    public List<MenuDTO> menus;
}