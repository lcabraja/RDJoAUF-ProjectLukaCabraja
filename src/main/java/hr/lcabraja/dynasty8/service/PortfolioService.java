package hr.lcabraja.dynasty8.service;

import hr.lcabraja.dynasty8.domain.Portfolio;

import javax.sound.sampled.Port;
import java.util.List;

public interface PortfolioService {

    long getCount();

    List<Portfolio> getAllPortfolios();

    Portfolio getPortfolioById(Long portfolioId);

    boolean deletePortfolio(Long portfolioId);

    Portfolio updatePortfolio(Portfolio portfolio);

    Portfolio createPortfolio(Portfolio portfolio);

//    Portfolio initializePortfolio(Portfolio portfolio);

}
