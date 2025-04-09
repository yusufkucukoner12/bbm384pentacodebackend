package pentacode.backend.code.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import jakarta.persistence.*;
import java.util.List;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    // Default constructor - JPA gereksinimi
    public User() {
    }

    // Getter ve Setter'lar
    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public List<Address> getAddresses() { // Koleksiyon için güncellendi
        return addresses;
    }

    public void setAddresses(List<Address> addresses) { // Koleksiyon için güncellendi
        this.addresses = addresses;
    }

    public List<SupportRequest> getSupportRequests() {
        return supportRequests;
    }

    public void setSupportRequests(List<SupportRequest> supportRequests) {
        this.supportRequests = supportRequests;
    }
}
