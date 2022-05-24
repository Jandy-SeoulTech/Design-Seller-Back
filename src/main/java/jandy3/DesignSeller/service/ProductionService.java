package jandy3.DesignSeller.service;

import jandy3.DesignSeller.oauth.exception.ResourceNotFoundException;
import jandy3.DesignSeller.domain.Production;
import jandy3.DesignSeller.repository.ProductionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductionService {
    private final ProductionRepository productionRepository;

//    public List<Production> findAll

    public Production findById(Long id) {
        Production production = productionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Production", "id", id));
        return production;
    }
    @Transactional(readOnly = false)
    public int updateView(Long id) {
        return productionRepository.updateView(id);
    }
}
