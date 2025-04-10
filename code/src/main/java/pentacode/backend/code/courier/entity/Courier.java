package pentacode.backend.code.courier.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import pentacode.backend.code.common.entity.Delivery;
import pentacode.backend.code.common.entity.User;
import pentacode.backend.code.restaurant.entity.Restaurant;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "courier")
@PrimaryKeyJoinColumn(name = "id")
public class Courier extends User {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "tax_number", nullable = false, length = 50)
    private String taxNumber;

    @Column(name = "availability_status")
    private Boolean availabilityStatus;

    @OneToOne
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery; // Tek bir Delivery ile ilişki
}