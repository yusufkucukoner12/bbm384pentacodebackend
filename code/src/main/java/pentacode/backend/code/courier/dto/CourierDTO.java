package pentacode.backend.code.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class CourierDTO {

    // Inherited from User
    private Long id; // User entity’sinin birincil anahtarı
    private String emailAddress;
    private String phoneNumber;
    private String passwordHash;
    private List<AddressDTO> addresses; // Nested AddressDTO
    private List<SupportRequestDTO> supportRequests; // Nested SupportRequestDTO

    // Specific to Courier
    private String name;
    private String surname;
    private String taxNumber;
    private Boolean availabilityStatus;
    private Long deliveryId; // Simplified representation of Delivery
}