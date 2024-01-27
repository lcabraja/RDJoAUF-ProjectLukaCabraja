package hr.lcabraja.dynasty8.controller;

import hr.lcabraja.dynasty8.domain.Portfolio;
import hr.lcabraja.dynasty8.domain.Apartment;
import hr.lcabraja.dynasty8.domain.User;
import hr.lcabraja.dynasty8.repository.ApartmentRepository;
import hr.lcabraja.dynasty8.service.PortfolioService;
import hr.lcabraja.dynasty8.service.ApartmentService;
import hr.lcabraja.dynasty8.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/apartment")
@AllArgsConstructor
public class ApartmentController {

    private final ApartmentService apartmentService;
    private final ApartmentRepository apartmentRepository;
    private final PortfolioService portfolioService;
    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Apartment> createApartment(@RequestBody Apartment apartment,
                                                     @RequestParam Long portfolioId) {
        Portfolio portfolio = portfolioService.getPortfolioById(portfolioId);
        apartment.setPortfolio(portfolio);
        Apartment createdApartment = apartmentService.createApartment(apartment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdApartment);
    }

    @PutMapping("/update")
    public ResponseEntity<Apartment> updateApartment(@RequestBody Apartment apartment) {
        Apartment old = apartmentService.getApartmentById(apartment.getId());
        apartment.setPortfolio(old.getPortfolio());
        Apartment fuckyou = apartmentRepository.save(apartment);
        Apartment updatedApartment = apartmentService.updateApartment(apartment);
        if (fuckyou == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(fuckyou);
    }

//    @PutMapping("/update")
//    public ResponseEntity<Apartment> updateApartment(@RequestAttribute("username") final String username, @RequestBody Apartment apartment) {
//        Optional<User> optionalUser = userService.findUserByUsername(username);
//        if (optionalUser.isPresent()) {
//            User user = optionalUser.get();
//            Apartment a = apartmentService.getApartmentById(apartment.getId());
//            if (a == null) {
//                return ResponseEntity.notFound().build();
//            }
//
//            a.setName(apartment.getName());
////            a.setUser(user);
//            try {
//                apartmentService.updateApartment(a);
//            } catch (Error e) {
//                System.err.println(e);
//                System.exit(1);
//            }
//            return ResponseEntity.ok(a);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteApartment(@RequestParam Long apartmentId) {
        apartmentRepository.deleteById(apartmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get")
    public ResponseEntity<Apartment> getApartment(@RequestAttribute("username") final String username, @RequestParam Long apartmentId) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        Apartment apartment = apartmentRepository.getReferenceById(apartmentId);
//        Apartment apartment = apartmentService.getApartmentById(apartmentId);
        if (apartment.getPortfolio().getUser().getId().equals(user.getId())) {
            return ResponseEntity.ok(apartment);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Apartment>> getAllApartments(@RequestAttribute("username") final String username) {
        Optional<User> optionalUser = userService.findUserByUsername(username);
        if (optionalUser.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User user = optionalUser.get();
        List<Apartment> apartments = apartmentService.getAllApartments().stream().filter(a -> a.getPortfolio().getUser().equals(user)).toList();
        return ResponseEntity.ok(apartments);
    }
}

