package pentacode.backend.code.common.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.*;
import pentacode.backend.code.common.entity.base.BaseAudityModel;
import pentacode.backend.code.courier.entity.Courier;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "delivery")
public class Delivery extends BaseAudityModel {

    @Column(name = "date", nullable = false)
    private LocalDateTime date;

    @OneToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier;

    @OneToMany
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

}