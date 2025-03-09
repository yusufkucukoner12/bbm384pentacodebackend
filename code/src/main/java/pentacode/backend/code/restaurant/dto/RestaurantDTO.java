package pentacode.backend.code.restaurant.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantDTO {
    public String name;
    public Long pk;
    public Integer version;


}