package pentacode.backend.code.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pentacode.backend.code.auth.entity.User;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private User user;
}

