package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Market;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MarketRepository {

    private final EntityManager em;
    private final UserRepository userRepository;

    public void save(Market market) {
        em.persist(market);
    }

    public Market findOne(Long id) {
        return em.find(Market.class, id);
    }

    public List<Market> findByUserId(Long userId) {
        return em.createQuery("select m from Market m where m.user.id = :userId", Market.class)
                .setParameter("userId", userId)
                .getResultList();
    }
}
