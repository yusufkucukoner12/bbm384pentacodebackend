package pentacode.backend.code.auth;

import java.util.Set;

import lombok.Data;
import pentacode.backend.code.auth.entity.Role;

@Data
public class LoginRequest {
    private String username;
    private String password;
    private Set<Role> authorities;
}
