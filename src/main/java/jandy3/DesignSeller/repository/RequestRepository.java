package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class RequestRepository {

    private final EntityManager em;

    public void save(Request request) {
        em.persist(request);
    }
}