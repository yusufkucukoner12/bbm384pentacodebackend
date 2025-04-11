package pentacode.backend.code.common.entity;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentacode.backend.code.common.entity.base.BaseAudityModel;
import pentacode.backend.code.customer.entity.Customer;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.entity.Restaurant;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseAudityModel{
    private String name;

    @Column(name = "order_status", nullable = false, length = 50)
    private String orderStatus;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "is_cart", nullable = false)
    private Boolean isCart;

    @Column(name = "order_cost", nullable = false)
    private Integer orderCost;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable=false)
    private Restaurant restaurant;

    @ManyToOne
    @JoinColumn(name = "delivery_id", nullable=false)
    private Delivery delivery;

    @ManyToMany
    @JoinTable(name = "order_menu",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menu> menus;
}
