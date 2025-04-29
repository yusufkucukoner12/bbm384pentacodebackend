package pentacode.backend.code.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import pentacode.backend.code.auth.service.AuthenticationService;
import pentacode.backend.code.auth.service.LoginService;
import pentacode.backend.code.auth.service.TokenService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationService authenticationService;
    private final LoginService loginService;
    private final TokenService tokenService;

    public AuthController(AuthenticationService authenticationService, LoginService loginService, TokenService tokenService) {
        this.authenticationService = authenticationService;
        this.loginService = loginService;
        this.tokenService = tokenService;
    }

    @PostMapping("/register")
    public ResponseEntity<CreateUserResponse> addNewUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        return new ResponseEntity<>(authenticationService.createUser(createUserRequest),HttpStatus.CREATED);
    }
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> generateToken(@Valid @RequestBody LoginRequest authRequest) {
        return new ResponseEntity<>(loginService.login(authRequest), HttpStatus.OK);
    }
    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken(@RequestBody String token) {
        if (tokenService.isTokenValid(token)) {
            return new ResponseEntity<>("Token is valid", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Token is invalid", HttpStatus.UNAUTHORIZED);
        }
    }

}
