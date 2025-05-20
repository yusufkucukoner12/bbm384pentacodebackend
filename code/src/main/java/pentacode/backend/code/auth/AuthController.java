package pentacode.backend.code.auth;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.persistence.EntityNotFoundException;
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
    @PostMapping("/admin-login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest authRequest) {
        return new ResponseEntity<>(loginService.login(authRequest), HttpStatus.OK);
    }
    @GetMapping("/validate-token")
    public ResponseEntity<String> validateToken() {
        return new ResponseEntity<>("Token is valid", HttpStatus.OK);
    }
    @PostMapping("/ban-user")
    public ResponseEntity<String> banUser(@RequestBody Long userId) {
        try {
            authenticationService.banUser(userId);
            return ResponseEntity.ok("User banned successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Something went wrong.");
        }
    }
    
}
