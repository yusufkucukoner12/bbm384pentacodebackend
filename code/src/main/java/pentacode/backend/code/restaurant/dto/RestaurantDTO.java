package pentacode.backend.code.restaurant.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class RestaurantDTO {
    public String name;
    public Long pk;
    public Integer version;

    public RestaurantDTO(String name, Long pk, Integer version) {
        this.name = name;
        this.pk = pk;
        this.version = version; 
    }

}