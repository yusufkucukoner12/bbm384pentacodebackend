package pentacode.backend.code.common.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentacode.backend.code.common.entity.base.BaseAudityModel;
import pentacode.backend.code.courier.entity.Courier;
import pentacode.backend.code.restaurant.entity.Menu;
import pentacode.backend.code.restaurant.entity.Restaurant;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
@NoArgsConstructor
public class Order extends BaseAudityModel {
    private String name;
    
    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    
    @ManyToMany
    @JoinTable(name = "order_menu",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menu> menus;
    
    @ManyToOne
    @JoinColumn(name = "courier_id")
    private Courier courier;
    
    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PLACED;
    
    private boolean courierAssignmentAccepted = false;
}