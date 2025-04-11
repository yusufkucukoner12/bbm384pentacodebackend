package pentacode.backend.code.courier.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import java.util.List;
import lombok.AllArgsConstructor;
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
public class Courier extends BaseAudityModel {
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String phoneNumber;
    
    @Column(nullable = false)
    private boolean isAvailable;
    
    @Column(nullable = false)
    private boolean isOnline;
    
    @OneToMany(mappedBy = "courier")
    private List<Order> orders;
}