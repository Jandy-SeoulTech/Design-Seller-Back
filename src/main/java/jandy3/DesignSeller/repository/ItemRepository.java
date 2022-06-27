package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        em.persist(item);
    }

    public List<Item> findAll(Pageable pageable) {
        return em.createQuery("select i from Item i")
                .setFirstResult(pageable.getPageNumber())
                .setMaxResults(pageable.getPageSize())
                .getResultList();
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }
}
