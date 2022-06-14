package jandy3.DesignSeller.service;

import jandy3.DesignSeller.exception.ResourceNotFoundException;
import jandy3.DesignSeller.domain.Production;
import jandy3.DesignSeller.repository.ProductionPageRepository;
import jandy3.DesignSeller.repository.ProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductionService {
    private final ProductionRepository productionRepository;

    private final ProductionPageRepository productionPageRepository;
//    public List<Production> findAll

    public Production findById(Long id) {
        return productionRepository.findById(id);
    }
    @Transactional(readOnly = false)
    public void updateView(Long id) {
        productionRepository.updateView(id);
    }

    public List<Production> getPostListPage(Pageable pageable) {
        Page<Production> postPage = productionPageRepository.findAll(pageable);
        List<Production> posts = postPage.getContent();
        return posts;
    }
}
