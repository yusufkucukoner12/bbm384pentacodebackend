package pentacode.backend.code.customer.dto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.common.dto.AddressDTO;
import pentacode.backend.code.common.dto.SupportRequestDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class CustomerDTO {

    // Inherited from User
    private Long id; // User entity’sinin birincil anahtarı
    private String emailAddress;
    private String phoneNumber;
    private String passwordHash;
    private List<AddressDTO> addresses; // Nested AddressDTO
    private List<SupportRequestDTO> supportRequests; // Nested SupportRequestDTO

    // Specific to Customer
    private String name;
    private String surname;
    private int balance;
    private List<Long> paymentMethodIds; // Simplified representation of PaymentMethods
    private List<Long> orderIds; // Simplified representation of Orders
}