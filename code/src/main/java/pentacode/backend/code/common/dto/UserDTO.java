package pentacode.backend.code.common.dto;
import org.springframework.web.bind.annotation.RequestParam;

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