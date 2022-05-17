package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.yaml.snakeyaml.error.Mark;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MarketService {
    private final MarketRepository marketRepository;
    @Transactional(readOnly = false)
    public Long createMarket(Market market) {
        validateDuplicateMarket(market.getUser().getId());
        marketRepository.save(market);
        return market.getId();
    }

    private void validateDuplicateMarket(Long userId) {
        Optional<Market> market = marketRepository.findByUserId(userId);
        if(!market.isEmpty()){
            throw new IllegalStateException("already exist");
        }
    }

}
