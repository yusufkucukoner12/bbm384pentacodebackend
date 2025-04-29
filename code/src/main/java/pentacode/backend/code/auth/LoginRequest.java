package pentacode.backend.code.auth;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
