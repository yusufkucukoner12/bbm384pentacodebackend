package pentacode.backend.code.auth.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import pentacode.backend.code.admin.Admin;
import pentacode.backend.code.admin.AdminRepository;
import pentacode.backend.code.auth.CreateUserRequest;
import pentacode.backend.code.auth.CreateUserResponse;
import pentacode.backend.code.auth.LoginRequest;
import pentacode.backend.code.auth.LoginResponse;
import pentacode.backend.code.auth.entity.Role;
import pentacode.backend.code.auth.entity.Token;
import pentacode.backend.code.auth.entity.User;
import pentacode.backend.code.auth.repository.TokenRepository;
import pentacode.backend.code.auth.repository.UserRepository;
import pentacode.backend.code.courier.entity.Courier;
import pentacode.backend.code.courier.repository.CourierRepository;
import pentacode.backend.code.customer.entity.Customer;
import pentacode.backend.code.customer.repository.CustomerRepository;
import pentacode.backend.code.restaurant.entity.Restaurant;
import pentacode.backend.code.restaurant.repository.RestaurantRepository;

@Service
public class AuthenticationService implements UserDetailsService{
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;
    private final RestaurantRepository restaurantRepository;
    private final CourierRepository courierRepository;
    private final CustomerRepository customerRepository;
    private final AdminRepository adminRepository;


 
    
    public AuthenticationService(UserRepository userRepository, 
                                  BCryptPasswordEncoder bCryptPasswordEncoder,
                                  JwtService jwtService,
                                  TokenRepository tokenRepository, RestaurantRepository restaurantRepository,
                                  CourierRepository courierRepository,
                                  CustomerRepository customerRepository,
                                  AdminRepository adminRepository) {

        this.restaurantRepository = restaurantRepository;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.courierRepository = courierRepository;
        this.customerRepository = customerRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        return user.orElseThrow(EntityNotFoundException::new);
    }

    public CreateUserResponse createUser(CreateUserRequest request) {
        Set<Role> roles = request.getAuthorities();

        if(!userRepository.findByEmailAndAuthorities(request.getEmail(), roles).isEmpty()){
            return CreateUserResponse.builder()
                    .message("Email already exists")
                    .build();
        }
        if(!userRepository.findByUsernameAndAuthorities(request.getUsername(), roles).isEmpty()){
            return CreateUserResponse.builder()
                    .message("Username already exists")
                    .build();
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

        Role role = roles.stream().findFirst().orElse(null);


        if(role.equals(Role.ROLE_RESTAURANT)){
            // initialize Restaurant object
            Restaurant restaurant = new Restaurant();
            restaurant.setName("Yusuf'un dükkanı...");
            restaurantRepository.save(restaurant);
            newUser.setRestaurant(restaurant);
        }
        else if(role.equals(Role.ROLE_COURIER)){
            // initialize Courier object
            Courier courier = new Courier();
            courier.setName(request.getName());
            courier.setPhoneNumber("05523375123");
            courier.setAvailable(true);
            courier.setOnline(true);
            
            courierRepository.save(courier);
            newUser.setCourier(courier);
        }
        else if(role.equals(Role.ROLE_CUSTOMER)){
            // initialize Customer object
            Customer customer = new Customer();
            customerRepository.save(customer);
            newUser.setCustomer(customer);
        }
        else if(role.equals(Role.ROLE_ADMIN)){
            // initialize Admin object
            Admin admin = new Admin();
            adminRepository.save(admin);
            newUser.setAdmin(admin);
        }

        var registeredUser = userRepository.save(newUser);
        var token = jwtService.generateToken(registeredUser.getUsername());
        saveToken(registeredUser, token);
        return CreateUserResponse.builder()
                .user(registeredUser)
                .message("Success").build();
    }


    public void saveToken(User user, String stringToken) {
        user.setToken(stringToken);
        Token token = Token.builder()
                .user_token(stringToken)
                .user(user)
                .revoked(false)
                .expired(false)
                .build();
        tokenRepository.save(token);
        user.setTokenEntity(token);
        userRepository.save(user);
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

    public Optional<User> getByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
