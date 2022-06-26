package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;

import javax.persistence.NoResultException;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;

    @Transactional
    public Long createMarket(Market market) {
        validateDuplicateMarket(market.getUser().getId());
        marketRepository.save(market);
        return market.getId();
    }

    private void validateDuplicateMarket(Long userId) {
        try{
            Market market = marketRepository.findByUserId(userId);
        } catch(NoResultException e) {
            return;
        }
        throw new IllegalStateException("market already exist");
    }
    public Market findByUserId(Long userId) {
        return marketRepository.findByUserId(userId);
    }
}
