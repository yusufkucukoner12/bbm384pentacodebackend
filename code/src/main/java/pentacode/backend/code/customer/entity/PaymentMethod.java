package pentacode.backend.code.customer.entity;

import jakarta.persistence.Column;
import jakarta.persistence.*;

import jakarta.persistence.*;
import pentacode.backend.code.common.entity.BaseAudityModel;

@Entity
@Getter
@Setter
@Table(name = "payment_method")
public class PaymentMethod extends BaseAudityModel {

    @Column(name = "payment_method_name", nullable = false, length = 50)
    private String paymentMethodName;

    @Column(name = "card_number", nullable = false, length = 50)
    private String cardNumber;

    @Column(name = "last_usage_month", nullable = false, length = 2)
    private String lastUsageMonth;

    @Column(name = "last_usage_day", nullable = false, length = 2)
    private String lastUsageDay;

    @Column(name = "card_owner_name", nullable = false, length = 50)
    private String cardOwnerName;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}