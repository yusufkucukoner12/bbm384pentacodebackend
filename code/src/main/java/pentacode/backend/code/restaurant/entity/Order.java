package pentacode.backend.code.restaurant.entity;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.common.entity.BaseAudityModel;

@Entity
@Getter
@Setter
@Table(name = "orders")
@AllArgsConstructor
public class Order extends BaseAudityModel{
    private String name;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable=false, insertable=false, updatable=false)
    private Restaurant restaurant;

    @ManyToMany
    @JoinTable(name = "order_menu",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private List<Menu> menus;
}
