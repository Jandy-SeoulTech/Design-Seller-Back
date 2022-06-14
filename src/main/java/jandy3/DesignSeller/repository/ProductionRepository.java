package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Production;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class ProductionRepository {

    private final EntityManager em;

    public int updateView(Long id) {
        return em.createQuery("update Production p set p.view = p.view + 1 where p.id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    public Production findById(Long id) {
        return em.createQuery("select p from Production p where p.id = :id", Production.class)
                .setParameter("id", id)
                .getSingleResult();
    }
}
