package pentacode.backend.code.customer.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PaymentMethodDTO {

    private Long id; // PaymentMethod entity’sinin birincil anahtarı
    private String paymentMethodName;
    private String cardNumber;
    private String lastUsageMonth;
    private String lastUsageDay;
    private String cardOwnerName;
    private String cvv;
    private Long customerId; // Simplified representation of Customer
}