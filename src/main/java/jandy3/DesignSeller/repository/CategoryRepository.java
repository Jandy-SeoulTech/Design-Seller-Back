package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;
import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByParentId(Long parent);
}
