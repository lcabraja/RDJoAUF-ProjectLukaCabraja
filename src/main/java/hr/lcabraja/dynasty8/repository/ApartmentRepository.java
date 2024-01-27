package hr.lcabraja.dynasty8.repository;

import hr.lcabraja.dynasty8.domain.Apartment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApartmentRepository extends JpaRepository<Apartment, Long> {
}
