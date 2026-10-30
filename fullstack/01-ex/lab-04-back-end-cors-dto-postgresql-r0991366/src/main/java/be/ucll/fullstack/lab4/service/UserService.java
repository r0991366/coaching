package be.ucll.fullstack.lab4.service;

import be.ucll.fullstack.lab4.model.Role;
import be.ucll.fullstack.lab4.model.User;
import be.ucll.fullstack.lab4.repository.UserRepository;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class UserService {
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String signup(@NotNull String name, Role role) {
        User user = new User(name, LocalDate.now(), role);
        userRepository.save(user);
        return user.getName();  // well this makes zero sense
    }


}
