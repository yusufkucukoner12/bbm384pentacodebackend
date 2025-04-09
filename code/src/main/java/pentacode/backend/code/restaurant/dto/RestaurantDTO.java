package pentacode.backend.code.restaurant.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class RestaurantDTO {
    public String name;
    public Long pk;
    public Integer version;
}