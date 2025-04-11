package pentacode.backend.code.customer.entity;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.persistence.*;
import pentacode.backend.code.common.entity.Order;
import pentacode.backend.code.common.entity.User;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "customer")
@PrimaryKeyJoinColumn(name = "id")
public class Customer extends User {

    @Column(name = "name", nullable = false, length = 50)
    private String name;

    @Column(name = "surname", nullable = false, length = 50)
    private String surname;

    @Column(name = "balance", nullable = false)
    private int balance;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<PaymentMethod> paymentMethods = new ArrayList<>();

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL)
    private List<Order> orders;
}