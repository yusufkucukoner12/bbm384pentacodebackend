package pentacode.backend.code.customer.dto;

import java.util.*;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.common.entity.Order;

@Getter
@Setter
@Builder
public class CustomerDTO {
    public String name;
    public String address;
    private float longitude;
    private float latitude;
    public String phoneNumber;
    public String email;
    public Order order;
    public List<Order> orderHistory;
}
