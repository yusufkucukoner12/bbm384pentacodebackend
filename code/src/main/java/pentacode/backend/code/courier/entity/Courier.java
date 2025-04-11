package pentacode.backend.code.courier.entity;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

import pentacode.backend.code.common.entity.Delivery;
import pentacode.backend.code.common.entity.User;

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