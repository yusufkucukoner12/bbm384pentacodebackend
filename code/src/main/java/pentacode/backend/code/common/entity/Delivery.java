package pentacode.backend.code.common.entity;

import jakarta.persistence.*;
import pentacode.backend.code.courier.entity.Courier;

import java.time.LocalDateTime;

@Entity
@Table(name = "delivery")
public class Delivery extends BaseAudityModel{

    @Column(name = "date", nullable = false)
    private LocalDateTime date; // TIMESTAMP için uygun Java türü

    @OneToOne
    @JoinColumn(name = "courier_id", nullable = false)
    private Courier courier; // Foreign key: Courier tablosuna referans

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order; // Foreign key: Order tablosuna referans

    public Delivery() {
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Courier getCourier() {
        return courier;
    }

    public void setCourier(Courier courier) {
        this.courier = courier;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}