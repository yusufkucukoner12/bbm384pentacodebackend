package pentacode.backend.code.admin.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import pentacode.backend.code.common.entity.User;

@Getter
@Setter
@Entity
@Table(name = "admin")
@PrimaryKeyJoinColumn(name = "id")
public class Admin extends User {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String surname;
}

