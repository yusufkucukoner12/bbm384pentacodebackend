package pentacode.backend.code.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.common.entity.base.BaseAudityModel;

@Getter
@Setter
@Entity
@Table(name = "support_request")
public class SupportRequest extends BaseAudityModel {

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
