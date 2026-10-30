package be.ucll.se.courses.backend.service;

import be.ucll.se.courses.backend.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailsImpl loadUserByUsername(String username)  {
        return new UserDetailsImpl(userRepository.findByUsername(username).orElseThrow(() ->new UsernameNotFoundException("User not found: " + username)));
    }

    public UserDetailsImpl updatePassword(UserDetailsImpl userDetails, String newPassword) {
        final var user = userDetails.user();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        return new UserDetailsImpl(user);
    }
}
