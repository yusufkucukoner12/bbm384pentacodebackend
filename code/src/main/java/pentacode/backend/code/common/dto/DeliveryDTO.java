package pentacode.backend.code.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class DeliveryDTO {
    private Long id;
    private LocalDateTime date;
    private Long courierId;
    private Long orderId;
}