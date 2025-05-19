package pentacode.backend.code.customer.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.entity.base.BaseAudityModel;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Customer extends BaseAudityModel{
    private String address;
    private String phoneNumber;
    private String name;

    private float longitude;
    private float latitude;
    @Email
    private String email;

    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "pk")
    private Order order;

    @OneToMany(mappedBy = "customer", fetch=FetchType.EAGER)
    private List<Order> orderHistory = new ArrayList<>();

    public void addOrderToHistory(Order order) {
        this.orderHistory.add(order);
    }
}
