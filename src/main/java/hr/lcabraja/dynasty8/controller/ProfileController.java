package hr.lcabraja.dynasty8.controller;

import hr.lcabraja.dynasty8.config.JwtService;
import hr.lcabraja.dynasty8.domain.User;
import hr.lcabraja.dynasty8.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/profile")
@AllArgsConstructor
public class ProfileController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping(value = "/picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadProfilePicture(
            @RequestPart("file") MultipartFile file,
            @RequestHeader("Authorization") String userToken
    ) {
        userToken = userToken.replace("Bearer ", "");
        String username = jwtService.extractUsername(userToken);
        Optional<User> userOptional = userService.findUserByUsername(username);

        // Check if the user is the database
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User can not be find, try to log in again");
        }

        // Check if the uploaded file is a picture
        if (!Objects.requireNonNull(file.getContentType()).startsWith("image/")) {
            return ResponseEntity.badRequest().body("Only image files are allowed.");
        }
        try {
            // Process the image file
            byte[] fileBytes = file.getBytes();
            User user = userOptional.get();
            user = userService.createUser(user);
            return  ResponseEntity.ok(user);
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Something went wrong while saving image to server");
        }
    }
}
