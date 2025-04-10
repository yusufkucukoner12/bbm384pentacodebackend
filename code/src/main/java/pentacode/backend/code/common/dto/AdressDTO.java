package pentacode.backend.code.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pentacode.code.backend.common.dto.UserDTO;
@Getter
@Setter
@Builder
public class AddressDTO {
    private String addressName;
    private String city;
    private String district;
    private String neighbourhood;
    private String street;
    private String buildingNumber;
    private String doorNumber;
    private Long userId;
}