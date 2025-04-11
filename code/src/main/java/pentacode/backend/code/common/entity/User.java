package pentacode.backend.code.common.entity;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import jakarta.persistence.*;
import java.util.List;
import pentacode.backend.code.common.entity.base.BaseAudityModel;

import java.util.ArrayList;

@Entity
@Getter
@Setter
@Table(name = "users") // "user" SQL'de rezerve kelimedir, güvenli olması için "users"
public class User extends BaseAudityModel {

    @Column(name = "email_address", nullable = false, length = 50)
    private String emailAddress;

    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Column(name = "password_hash", nullable = false, length = 50)
    private String passwordHash;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<SupportRequest> supportRequests;
}
