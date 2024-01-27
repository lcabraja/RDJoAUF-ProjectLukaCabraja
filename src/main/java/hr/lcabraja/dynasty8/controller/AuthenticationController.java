package hr.lcabraja.dynasty8.controller;

import hr.lcabraja.dynasty8.config.AuthenticationService;
import hr.lcabraja.dynasty8.domain.LoginRequest;
import hr.lcabraja.dynasty8.domain.AuthenticationResponse;
import hr.lcabraja.dynasty8.domain.RegisterRequest;
import hr.lcabraja.dynasty8.domain.User;
import hr.lcabraja.dynasty8.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request
    ) {
        System.out.println(request);
        if (authenticationService.isUserAlreadyExist(request)) {
            return ResponseEntity.badRequest().body("User Already exists");
        }
        if (!authenticationService.isUserHaveAllFields(request)) {
            return ResponseEntity.badRequest().body("Missing one of the fields");
        }

        return ResponseEntity.ok(authenticationService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody LoginRequest request
    ) {
        return ResponseEntity.ok(authenticationService.login(request));
    }


    @GetMapping("/valid")
    public ResponseEntity<User> valid(@RequestAttribute("username") final String username) {
        Optional<User> user = userService.findUserByUsername(username);
        if (user.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user.get());
    }
}
