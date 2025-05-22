package pentacode.backend.code.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.courier.dto.CourierDTO;
import pentacode.backend.code.customer.dto.CustomerDTO;
import pentacode.backend.code.restaurant.dto.RestaurantDTO;
import pentacode.backend.code.restaurant.entity.Restaurant;

@Getter
@Setter
@Builder
public class ReviewDTO {
    private Long pk;
    private String reviewText;
    private Integer rating;
    private RestaurantDTO restaurant;
    private CourierDTO courier;
    private CustomerDTO customer;
}
