package hr.lcabraja.dynasty8.service;

import hr.lcabraja.dynasty8.domain.Portfolio;
import hr.lcabraja.dynasty8.repository.PortfolioRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;

    @Override
    public long getCount() {
        return portfolioRepository.count();
    }

    @Override
    public List<Portfolio> getAllPortfolios() {
        return portfolioRepository.findAll();
    }

    @Override
    public Portfolio getPortfolioById(Long portfolioId) {
        return portfolioRepository.findById(portfolioId).orElse(null);
    }

    @Override
    public boolean deletePortfolio(Long portfolioId) {
        try {
            portfolioRepository.deleteById(portfolioId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public Portfolio updatePortfolio(Portfolio portfolio) { return portfolioRepository.save(portfolio); }

    @Override
    public Portfolio createPortfolio(Portfolio portfolio) {
        return portfolioRepository.save(portfolio);
    }

//    @Override
//    public Portfolio initializePortfolio(Portfolio portfolio) {
//
//        return portfolioRepository.save()
//    }
}

