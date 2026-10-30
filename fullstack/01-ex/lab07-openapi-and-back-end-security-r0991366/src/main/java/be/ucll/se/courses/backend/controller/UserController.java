package be.ucll.se.courses.backend.controller;

import be.ucll.se.courses.backend.controller.dto.AuthenticationRequest;
import be.ucll.se.courses.backend.controller.dto.AuthenticationResponse;
import be.ucll.se.courses.backend.controller.dto.UserInput;
import be.ucll.se.courses.backend.model.Role;
import be.ucll.se.courses.backend.model.User;
import be.ucll.se.courses.backend.service.UserService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.apache.tomcat.util.http.SameSiteCookies;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getUsers() {
        return userService.getAllUsers();
    }

    @PostMapping("/signup")
    public User signup(@Valid @RequestBody UserInput userInput) {
        return userService.signup(Role.GUEST, userInput);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> authenticate(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) {
        var auth = userService.authenticate(authenticationRequest.username(), authenticationRequest.password());
        ResponseCookie cookie = ResponseCookie.from("authToken", auth.token())
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(3600)
                .sameSite(SameSiteCookies.NONE.toString())
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());

        // only send token via cookie
        auth = new AuthenticationResponse(auth.message(), null, auth.username(), auth.fullname(), auth.role());
        return ResponseEntity.ok(auth);
    }
}
