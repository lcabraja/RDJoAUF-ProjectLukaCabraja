package hr.lcabraja.dynasty8.config;

import hr.lcabraja.dynasty8.domain.LogEntry;
import hr.lcabraja.dynasty8.domain.Role;
import hr.lcabraja.dynasty8.domain.User;
import hr.lcabraja.dynasty8.service.LogEntryService;
import hr.lcabraja.dynasty8.service.ApartmentService;
import hr.lcabraja.dynasty8.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@AllArgsConstructor
public class AdminUserInitializer implements ApplicationRunner {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        System.out.println( addAdminUser() ? "Admin user added successfully " : "Error while adding Admin user");
    }

    private boolean addAdminUser() {
        if (userService.findUserByUsername("admin").isEmpty()) {

            User adminUser = User.builder()
                    .username("admin")
                    .firstName("Ivan")
                    .lastName("Markic")
                    .language("hr")
                    .password(passwordEncoder.encode("Zvjerka"))
                    .role(Role.ADMIN)
                    .build();

            userService.createUser(adminUser);
        }
        return true;
    }
}
