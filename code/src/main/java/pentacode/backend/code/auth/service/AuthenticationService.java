package pentacode.backend.code.auth.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pentacode.backend.code.auth.CreateUserRequest;
import pentacode.backend.code.auth.CreateUserResponse;
import pentacode.backend.code.auth.LoginRequest;
import pentacode.backend.code.auth.LoginResponse;
import pentacode.backend.code.auth.entity.Token;
import pentacode.backend.code.auth.entity.User;
import pentacode.backend.code.auth.repository.TokenRepository;
import pentacode.backend.code.auth.repository.UserRepository;

@Service
public class AuthenticationService implements UserDetailsService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
 
    
    public AuthenticationService(UserRepository userRepository, 
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  JwtService jwtService,
                                  TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        if (!userRepository.findByUsername(request.getUsername()).isEmpty()) {
            return CreateUserResponse.builder().message("This username already used").build();
        }
        if (!userRepository.findByEmail(request.getEmail()).isEmpty()) {
            return CreateUserResponse.builder().message("This email already used").build();
        }
        User newUser = User.builder()
                .name(request.getName())
                .username(request.getUsername())
                .email(request.getEmail())
                .password(bCryptPasswordEncoder.encode(request.getPassword()))
                .authorities(request.getAuthorities())
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .isEnabled(true)
                .accountNonLocked(true)
                .build();

        var registeredUser = userRepository.save(newUser);
        var token = jwtService.generateToken(registeredUser.getUsername());
        registeredUser.setToken(token);
        saveToken(registeredUser, token);
        return CreateUserResponse.builder()
                .user(registeredUser)
                .message("Success").build();
    }


    public void saveToken(User user, String stringToken) {
        Token token = Token.builder()
                .user_token(stringToken)
                .user(user)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
    }

    public void allTokenExpired(User user) {
        List<Token> validUsersToken = tokenRepository.findAllValidTokensByUser(user.getId());
        if (validUsersToken.isEmpty()) {
            return;
        }
        for (Token token : validUsersToken) {
            token.setExpired(true);
            token.setRevoked(true);
        }
        tokenRepository.saveAll(validUsersToken);
    }
}
