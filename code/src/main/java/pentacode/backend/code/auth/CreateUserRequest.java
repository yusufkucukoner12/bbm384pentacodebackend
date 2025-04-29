package pentacode.backend.code.auth;


import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

import pentacode.backend.code.auth.entity.Role;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserRequest {
    private String name;

    private String username;

    private String password;

    @Email
    private String email;

    Set<Role> authorities;

    private String restaurantAddress;
    private String restaurantPhoneNumber;
    private String restaurantDescription;

    private String courierPhoneNumber;
    private boolean isAvailable;
    private boolean isOnline;

    private String customerAddress;
    private String customerPhoneNumber;
}

