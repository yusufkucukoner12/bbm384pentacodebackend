package pentacode.backend.code.restaurant.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class OrderDTO {
    public Long id;
    public String name;
}
