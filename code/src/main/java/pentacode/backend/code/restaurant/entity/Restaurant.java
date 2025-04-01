package pentacode.backend.code.restaurant.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.common.entity.BaseAudityModel;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Restaurant extends BaseAudityModel {
    @Column(name="restaurant_name", unique=true, nullable=false )
    private String name;
}