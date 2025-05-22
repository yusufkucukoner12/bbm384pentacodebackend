package pentacode.backend.code.auth.service;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pentacode.backend.code.auth.LoginRequest;
import pentacode.backend.code.auth.LoginResponse;
import pentacode.backend.code.auth.repository.TokenRepository;
import pentacode.backend.code.auth.entity.Role;
import pentacode.backend.code.auth.entity.User;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private final AuthenticationManager authenticationManager;
    private final AuthenticationService authenticationService;
    private final JwtService jwtService;

    public LoginService(AuthenticationManager authenticationManager, AuthenticationService authenticationService, JwtService jwtService, TokenRepository tokenRepository) {
        this.authenticationManager = authenticationManager;
        this.authenticationService = authenticationService;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest authRequest) {
        authRequest.setUsername(authRequest.getUsername()+authRequest.getAuthorities().toString());
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            var user = authenticationService.getByUsername(authRequest.getUsername()).orElseThrow();
            // Check for soft-deleted linked entities
            if ((user.getCustomer() != null && user.getCustomer().isDeleted()) ||
                (user.getRestaurant() != null && user.getRestaurant().isDeleted()) ||
                (user.getCourier() != null && user.getCourier().isDeleted())) {
                throw new RuntimeException("This user is deleted");
            }
            var stringToken = jwtService.generateToken(authRequest.getUsername());
            authenticationService.allTokenExpired(user);
            authenticationService.saveToken(user, stringToken);
            user.setToken(stringToken);
            if (user.isBanned()) {
                throw new RuntimeException("This user is banned");
            }
            
           
            return LoginResponse.builder().user(user).build();
        }
        throw new UsernameNotFoundException("invalid username {}" + authRequest.getUsername());
    }
 public LoginResponse adminLogin(LoginRequest authRequest) {
    try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            var user = authenticationService.getByUsername(authRequest.getUsername()).orElseThrow();
            if (user.getAdmin() == null || user.getAdmin().getPk() == null) {
                throw new AccessDeniedException("You are not authorized to log in as admin.");}
            var stringToken = jwtService.generateToken(authRequest.getUsername());
            authenticationService.allTokenExpired(user);
            authenticationService.saveToken(user, stringToken);
            user.setToken(stringToken);
            return LoginResponse.builder().user(user).build();
        } else {
            throw new BadCredentialsException("Authentication failed");
        }
    } catch (Exception e) {
    e.printStackTrace(); 
    throw new RuntimeException("Admin login failed: " + e.getMessage(), e);
}
}
}
