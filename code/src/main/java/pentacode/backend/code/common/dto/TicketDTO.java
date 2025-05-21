package pentacode.backend.code.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TicketDTO {
    private String ticketResponse;
    private boolean isResolved;
}
