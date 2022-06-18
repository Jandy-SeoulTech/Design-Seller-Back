package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.Production;
import jandy3.DesignSeller.repository.ProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductionService {
    private final ProductionRepository productionRepository;

    public Production findById(Long id) {
        return productionRepository.findOne(id);
    }
    @Transactional(readOnly = false)
    public void updateView(Long id) {
        productionRepository.updateView(id);
    }

    public List<Production> getProductionList(Long categoryId, Pageable pageable) {
        if(categoryId == null) {
            return productionRepository.findAll(pageable);
        }
        return productionRepository.findByCategoryId(categoryId, pageable);
    }
}
