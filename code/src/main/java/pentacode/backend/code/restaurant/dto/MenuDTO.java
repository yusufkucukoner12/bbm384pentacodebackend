package pentacode.backend.code.restaurant.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MenuDTO {
    public Long pk;
    public String name;
    public String description;
    public double price;
    private List<FoodDTO> foods;
}
