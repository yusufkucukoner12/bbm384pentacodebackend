package pentacode.backend.code.auth.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import pentacode.backend.code.auth.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String userName);
    Optional<User> findByEmail(String email);
}
