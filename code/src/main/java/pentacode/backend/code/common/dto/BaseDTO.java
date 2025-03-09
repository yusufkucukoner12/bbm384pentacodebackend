package pentacode.backend.code.common.dto;
import javax.xml.crypto.Data;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BaseDTO {
    public Long pk;
    public Integer version;
    public Data createdAt;
    public Data updatedAt;
}