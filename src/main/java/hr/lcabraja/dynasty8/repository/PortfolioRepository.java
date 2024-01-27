package hr.lcabraja.dynasty8.repository;

import hr.lcabraja.dynasty8.domain.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PortfolioRepository extends JpaRepository<Portfolio, Long>  {
}
