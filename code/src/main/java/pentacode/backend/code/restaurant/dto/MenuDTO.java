package pentacode.backend.code.restaurant.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MenuDTO {
    public String name;
    public String description;
    public double price;
    private List<FoodDTO> foods;
}
