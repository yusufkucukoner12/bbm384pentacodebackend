package pentacode.backend.code.restaurant.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pentacode.backend.code.common.entity.BaseAudityModel;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Food extends BaseAudityModel{
    private String name;
    private String description;
    private double price;

    @Column(name = "image_url", nullable=true)
    private String imageUrl;
    private String category;

    @Column(name = "is_available", nullable=true)
    private boolean isAvailable;
    
    @ManyToMany(mappedBy = "foods")
    private List<Menu> menus;

    @Column(name = "is_drink")
    private boolean isDrink;
}
