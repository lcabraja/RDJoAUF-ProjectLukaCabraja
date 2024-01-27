package hr.lcabraja.dynasty8.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import hr.lcabraja.dynasty8.repository.PortfolioRepository;
import hr.lcabraja.dynasty8.repository.LogEntryRepository;
import hr.lcabraja.dynasty8.repository.UserRepository;
import hr.lcabraja.dynasty8.service.LogEntryService;
import hr.lcabraja.dynasty8.service.LogEntryServiceImpl;
import hr.lcabraja.dynasty8.service.UserService;
import hr.lcabraja.dynasty8.service.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
@EnableScheduling
public class ApplicationConfig {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public UserService userService() {
        return new UserServiceImpl(userRepository, portfolioRepository);
    }

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer customizer() {
        return builder -> builder.featuresToDisable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }

    @Bean
    public LogEntryService logEntryService(LogEntryRepository logEntryRepository) {
        return new LogEntryServiceImpl(logEntryRepository);
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider
                = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
