package jandy3.DesignSeller.controller;

import jandy3.DesignSeller.dto.ProductionResponse;
import jandy3.DesignSeller.service.ProductionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ProductionController {
    private final ProductionService productionService;

    @GetMapping(value = "/production/{productionId}")
    public ProductionResponse getProductionById(@PathVariable Long productionId) {
        productionService.updateView(productionId);
        return new ProductionResponse(true, productionService.findById(productionId));
    }
}
