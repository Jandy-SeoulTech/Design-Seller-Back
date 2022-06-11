package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

    private final EntityManager em;

    public List<Category> findParentCategories() {
        return em.createQuery("select c from Category as c where c.parent.id is null", Category.class)
                .getResultList();
    }
}
