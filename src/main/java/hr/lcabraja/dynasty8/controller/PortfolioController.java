package hr.lcabraja.dynasty8.controller;

import hr.lcabraja.dynasty8.config.JwtService;
import hr.lcabraja.dynasty8.domain.Apartment;
import hr.lcabraja.dynasty8.domain.Portfolio;
import hr.lcabraja.dynasty8.domain.User;
import hr.lcabraja.dynasty8.repository.PortfolioRepository;
import hr.lcabraja.dynasty8.service.ApartmentService;
import hr.lcabraja.dynasty8.service.PortfolioService;
import hr.lcabraja.dynasty8.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import javax.sound.sampled.Port;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/portfolio")
@AllArgsConstructor
public class PortfolioController {

    private final PortfolioService portfolioService;
    private final PortfolioRepository portfolioRepository;
    private final ApartmentService apartmentService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Portfolio> createPortfolio(@RequestAttribute("username") final String username, @RequestBody Portfolio p) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            Portfolio np = new Portfolio();
            np.setName(p.getName());
            np.setUser(user);
            Portfolio cp = portfolioService.createPortfolio(np);

            for (Apartment a : p.getApartments()) {
                Apartment na = new Apartment();
                na.setName(a.getName());
                na.setDate(a.getDate());
                na.setDescription(a.getDescription());
                na.setPortfolio(a.getPortfolio());
                apartmentService.createApartment(na);
            }

            cp = portfolioService.getPortfolioById(cp.getId());

            // portfolio.setApartments(null);
            // Portfolio createdPortfolio = portfolioService.createPortfolio(portfolio); // Save the portfolio

            return ResponseEntity.status(HttpStatus.CREATED).body(cp);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/update")
    public ResponseEntity<Portfolio> updatePortfolio(@RequestAttribute("username") final String username, @RequestBody Portfolio portfolio) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Portfolio updatedPortfolio = portfolioService.getPortfolioById(portfolio.getId());
            if (updatedPortfolio == null) {
                return ResponseEntity.notFound().build();
            }

            updatedPortfolio.setName(portfolio.getName());
            updatedPortfolio.setUser(user);
            try {
                portfolioRepository.save(updatedPortfolio);
            } catch (Error e) {
                System.err.println(e);
                System.exit(1);
            }
            return ResponseEntity.ok(updatedPortfolio);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deletePortfolio(@RequestParam Long portfolioId) {
        if (portfolioService.deletePortfolio(portfolioId)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Portfolio> getPortfolio(@RequestAttribute("username") final String username, @RequestParam Long portfolioId) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
        if (portfolio != null && portfolio.getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(portfolio);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Portfolio>> getAllPortfolios(@RequestAttribute("username") final String username) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        List<Portfolio> portfolios = portfolioService.getAllPortfolios().stream().filter(p -> Objects.equals(p.getUser().getId(), user.getId())).toList();
        return ResponseEntity.ok(portfolios);
    }
}

