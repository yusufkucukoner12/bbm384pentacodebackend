package pentacode.backend.code.restaurant.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.entity.base.BaseAudityModel;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Restaurant extends BaseAudityModel {
    @Column(name="restaurant_name", unique=true, nullable=false )
    private String name;

    @OneToMany(mappedBy = "restaurant")
    private List<Order> orders;

    @OneToMany(mappedBy = "restaurant", fetch=FetchType.EAGER)
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant")
    private List<Category> categories;

    @ManyToOne
    private Restaurant restaurant;
    
    private String imageUrl;
    private String address;
    private String phoneNumber;
    private String description;

    @Email
    private String email;

    private String foodType;
    private String openingHours;
    private String closingHours;
    private Integer deliveryTime;
    private Integer deliveryFee;
    private Integer minOrderAmount;
    private Integer maxOrderAmount;

    private Double rating = 0.0;
    private Integer numberOfRatings = 0;
}