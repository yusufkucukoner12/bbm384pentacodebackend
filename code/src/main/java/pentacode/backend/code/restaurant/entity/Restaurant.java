package pentacode.backend.code.restaurant.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
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

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus;

    @OneToMany(mappedBy = "restaurant")
    private List<Category> categories;
    
    private String imageUrl;
    private String address;
    private String phoneNumber;
    private String description;

    @Email
    private String email;


}