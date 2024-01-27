package hr.lcabraja.dynasty8.config;

import hr.lcabraja.dynasty8.domain.LogEntry;
import hr.lcabraja.dynasty8.service.ApartmentService;
import hr.lcabraja.dynasty8.service.LogEntryService;
import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
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
public class Scheduler {
    private final LogEntryService logEntryService;
    private final ApartmentService apartmentService;

    @Scheduled(fixedRate = 1000 * 60)
    public void apartmentCountLogger() {
        //TODO Check if this is working or not
        LogEntry logEntry = LogEntry.builder()
                .message(apartmentService.getCount() + " apartments registered to service")
                .timestamp(new Date())
                .build();
        System.out.println(
                logEntry.getTimestamp() + " -- Number of apartments registered: " + apartmentService.getCount());
        logEntryService.saveLog(logEntry);
    }
}
