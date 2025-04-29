package pentacode.backend.code.auth.service;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import pentacode.backend.code.auth.LoginRequest;
import pentacode.backend.code.auth.LoginResponse;
import pentacode.backend.code.auth.repository.TokenRepository;

import org.springframework.security.core.Authentication;
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
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated()) {
            var user = authenticationService.getByUsername(authRequest.getUsername()).orElseThrow();
            var stringToken = jwtService.generateToken(authRequest.getUsername());
            authenticationService.allTokenExpired(user);
            authenticationService.saveToken(user, stringToken);
            user.setToken(stringToken);
            return LoginResponse.builder().user(user).build();
        }
        throw new UsernameNotFoundException("invalid username {}" + authRequest.getUsername());
    }

}
