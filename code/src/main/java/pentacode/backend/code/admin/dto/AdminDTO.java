package pentacode.backend.code.admin.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.common.dto.AddressDTO;
import pentacode.backend.code.common.dto.SupportRequestDTO;

import java.util.List;

@Getter
@Setter
@Builder
public class AdminDTO {

    // Inherited from User
    private Long id; // User entity’sinin birincil anahtarı
    private String emailAddress;
    private String phoneNumber;
    private String passwordHash;
    private List<AddressDTO> addresses; // Nested AddressDTO
    private List<SupportRequestDTO> supportRequests; // Nested SupportRequestDTO

    // Specific to Admin
    private String name;
    private String surname;
}