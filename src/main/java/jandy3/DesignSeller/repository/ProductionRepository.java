package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Production;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductionRepository extends JpaRepository<Production, Long> {
    @Modifying
    @Query("update Production p set p.view = p.view + 1 where p.id = :id")
    int updateView(@Param("id") Long id);

    Page<Production> findAll(Pageable pageable);
}
