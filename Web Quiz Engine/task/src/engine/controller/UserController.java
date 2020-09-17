package engine.controller;

import engine.model.User;
import engine.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Dionysios Stolis <dionstol@gmail.com>
 */
@RestController
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserController(final UserService userRepository, final PasswordEncoder passwordEncoder) {
        this.userService = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping(path = "/actuator/shutdown")
    public ResponseEntity<?> noAuth() {
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/api/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registerNewUser(@Valid @RequestBody final User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return ResponseEntity.ok(userService.addUser(user));
    }
}
