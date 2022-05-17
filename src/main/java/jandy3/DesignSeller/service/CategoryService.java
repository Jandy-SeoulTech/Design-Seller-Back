package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.Category;
import jandy3.DesignSeller.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public List<Category> findParents() {
        return categoryRepository.findByParentId(null);
    }


}
