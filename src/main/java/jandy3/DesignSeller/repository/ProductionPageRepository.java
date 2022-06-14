package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductionPageRepository extends JpaRepository<Production, Long> {
    Page<Production> findAll(Pageable pageable);
}
