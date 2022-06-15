package jandy3.DesignSeller.repository;


import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.domain.ProductionOption;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProductionOptionRepository {
    private final EntityManager em;

    public ProductionOption findOne(Long id) {
        return em.find(ProductionOption.class, id);
    }
}
