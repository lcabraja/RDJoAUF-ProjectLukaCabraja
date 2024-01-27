package hr.lcabraja.dynasty8.controller;

import hr.lcabraja.dynasty8.config.JwtService;
import hr.lcabraja.dynasty8.domain.User;
import hr.lcabraja.dynasty8.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
@AllArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping(value = {"/create", ""})
    public ResponseEntity<String> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User successfully created with ID: " + createdUser.getId());
    }

//    @PutMapping("/update")
//    public ResponseEntity<User> updateUser(@RequestBody User user) {
//        userService.updateUser(user);
//        return ResponseEntity.ok(user);
//    }

//    @DeleteMapping(value = "/delete")
//    public ResponseEntity<String> deleteUser(@RequestParam Long userId, @RequestHeader("Authorization") String userToken) {
//        userToken = userToken.substring(7);
//        String username = jwtService.extractUsername(userToken);
//        Optional<User> optionalUser = userService.findUserByUsername(username);
//        User userToDelete = userService.getUserById(userId);
//        if (optionalUser.isPresent()) {
//            if (userToDelete.getUsername().equals(optionalUser.get().getUsername()) || optionalUser.get().getRole() == Role.ADMIN) {
//                if (userService.deleteUser(userId)) {
//                    return ResponseEntity.noContent().build();
//                }
//            }
//            return ResponseEntity.badRequest().build();
//        }
//        return ResponseEntity.notFound().build();
//    }

//    @GetMapping(value = "/get/jwt")
//    public ResponseEntity<User> getUserByJwt(
//            @RequestHeader("Authorization") String userToken
//    ) {
//        if (userToken.length() < 7) {
//            return ResponseEntity.unprocessableEntity().build();
//        }
//        userToken = userToken.substring(7);
//        String username = jwtService.extractUsername(userToken);
//        Optional<User> optionalUser = userService.findUserByUsername(username);
//        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
//    }

    @GetMapping(value = "/get")
    public ResponseEntity<User> getUser(@RequestParam String username) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        return optionalUser.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Secured("ROLE_ADMIN")
    @GetMapping(value = "/all")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping(value = "/change-password")
    public ResponseEntity<User> changePassword(@RequestAttribute("username") final String username, @RequestParam String password) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setPassword(passwordEncoder.encode(password));
            userService.updateUser(user);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<String> deleteUser(@RequestAttribute("username") final String username) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            userService.deleteUser(user.getId());
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
