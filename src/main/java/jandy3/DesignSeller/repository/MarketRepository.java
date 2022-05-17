package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MarketRepository extends JpaRepository<Market, Long> {
    public Optional<Market> findByUserId(Long userId);
}
