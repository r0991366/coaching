package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.controller.dto.AuthenticationResponse;
import be.ucll.se.courses.backend.controller.dto.UserInput;
import be.ucll.se.courses.backend.exception.CoursesException;
import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Authenticates with a given username and password
     *
     * @param username the user's username
     * @param password the user's password (in plaintext)
     * @return an AuthenticationResponse containing a JWT
     */
    public AuthenticationResponse authenticate(String username, String password) {
        System.out.println(passwordEncoder.matches("admin", "$2a$12$XzII37KTt/27tbRtEOeCueaXzyE2DyovN1VMnTSdnaSn.T07eQA06"));
        final var usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(username, password);
        final var authentication = authenticationManager.authenticate(usernamePasswordAuthentication);
        final var user = ((UserDetailsImpl) authentication.getPrincipal()).user();
        final var token = jwtService.generateToken(user);
        return new AuthenticationResponse(
                "Authentication successful.",
                token,
                user.getUsername(),
                user.getFullName(),
                user.getRole()
        );
    }


    public User signup (Role role, UserInput userInput) {
        if (userRepository.existsByUsername(userInput.username())) {
            throw new CoursesException("Username is already in use.");
        }

        final var hashedPassword = passwordEncoder.encode(userInput.password());
        final var user = new User(
                userInput.username(),
                userInput.firstName(),
                userInput.lastName(),
                userInput.email(),
                hashedPassword,
                role
        );

        return userRepository.save(user);
    }
}
