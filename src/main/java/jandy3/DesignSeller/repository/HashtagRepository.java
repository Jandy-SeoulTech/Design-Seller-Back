package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Hashtag;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;

@Repository
@RequiredArgsConstructor
public class HashtagRepository {

    private final EntityManager em;

    public void save(Hashtag hashtag) {
        em.persist(hashtag);
    }
    public Hashtag findByName(String name) throws NoResultException {
        return em.createQuery("select h from Hashtag h where h.name = :name", Hashtag.class)
                .setParameter("name", name)
                .getSingleResult();
    }
}
