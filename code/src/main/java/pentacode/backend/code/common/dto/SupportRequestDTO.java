package pentacode.backend.code.common.dto;
import org.springframework.web.bind.annotation.RequestParam;

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

