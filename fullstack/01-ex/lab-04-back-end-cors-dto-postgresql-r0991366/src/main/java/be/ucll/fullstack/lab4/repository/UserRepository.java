package be.ucll.fullstack.lab4.repository;

import be.ucll.fullstack.lab4.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
