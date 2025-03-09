package pentacode.backend.code.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@MappedSuperclass
@EqualsAndHashCode(of = "pk")
@Getter
@Setter
public abstract class BaseModel {
    @Id
    @GeneratedValue
    @Column(name="pk", updatable=false, nullable=false)
    private Long pk;
    @Version
    private Integer version;
}