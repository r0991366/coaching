package be.ucll.se.courses.backend.repository.springdatajpa;

import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.UserRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

@Profile("!jdbc & !jpa")
public interface SpringDataJpaUserRepository extends JpaRepository<User, Long>, UserRepository {

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
