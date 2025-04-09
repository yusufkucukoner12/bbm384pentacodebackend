package pentacode.backend.code.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;

import jakarta.persistence.*;

@Entity
@Table(name = "support_request")
public class SupportRequest extends BaseAudityModel {

    @Column(name = "message", nullable = false, columnDefinition = "TEXT")
    private String message;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // telefon ve şifre hash’i User'dan alınır

    // Getter ve Setter
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
