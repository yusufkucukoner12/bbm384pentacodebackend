package pentacode.backend.code.common.dto;
import pentacode.code.backend.common.dto.UserDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SupportRequestDTO {
    private Long id;
    private String message;
    private Long userId;
}

