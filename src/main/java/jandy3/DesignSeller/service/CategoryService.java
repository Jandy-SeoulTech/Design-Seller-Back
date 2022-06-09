package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.Category;
import jandy3.DesignSeller.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findParents() {
        return categoryRepository.findByParentId(null);
    }


}
