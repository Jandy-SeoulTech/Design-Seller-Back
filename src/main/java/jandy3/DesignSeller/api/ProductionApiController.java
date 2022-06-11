package jandy3.DesignSeller.api;

import jandy3.DesignSeller.dto.ProductionListResponse;
import jandy3.DesignSeller.dto.ProductionResponse;
import jandy3.DesignSeller.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductionApiController {
    private final ProductionService productionService;

    @GetMapping(value ="/production/list")
    public ProductionListResponse getProductionList(Pageable pageable) {
        return new ProductionListResponse(true, productionService.getPostListPage(pageable));
    }

    @GetMapping(value = "/production/{productionId}")
    public ProductionResponse getProductionById(@PathVariable Long productionId) {
        productionService.updateView(productionId);
        return new ProductionResponse(true, productionService.findById(productionId));
    }
}
