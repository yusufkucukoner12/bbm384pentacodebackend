package pentacode.backend.code.common.dto;
import pentacode.code.backend.common.dto.AddressDTO;
import pentacode.code.backend.common.dto.SupportRequestDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserDTO {
    private Long id;
    private String emailAddress;
    private String phoneNumber;
    private String passwordHash;
    private List<AddressDTO> addresses;
    private List<SupportRequestDTO> supportRequests;
}