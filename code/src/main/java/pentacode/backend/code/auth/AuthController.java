package pentacode.backend.code.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pentacode.backend.code.auth.service.AuthenticationService;
import pentacode.backend.code.auth.service.LoginService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final LoginService loginService;

    public AuthController(AuthenticationService authenticationService, LoginService loginService) {
        this.authenticationService = authenticationService;
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> addNewUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(authenticationService.createUser(createUserRequest),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> generateToken(@Valid @RequestBody LoginRequest authRequest) {
        return ResponseEntity.ok(loginService.login(authRequest));
    }

}
