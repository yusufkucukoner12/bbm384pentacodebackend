package pentacode.backend.code.common.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReviewDTO {
    private Long pk;
    private String reviewText;
    private Integer rating;
}
