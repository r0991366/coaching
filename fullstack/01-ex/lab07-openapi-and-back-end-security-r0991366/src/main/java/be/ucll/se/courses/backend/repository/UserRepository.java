package be.ucll.se.courses.backend.repository;

import be.ucll.se.courses.backend.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {

    User save(User user);

    void deleteAll();

    Optional<User> findById(Long id);

    List<User> findAll();

    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
}
