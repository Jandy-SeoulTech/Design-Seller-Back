package jandy3.DesignSeller.api;

import jandy3.DesignSeller.domain.Production;
import jandy3.DesignSeller.domain.ProductionImage;
import jandy3.DesignSeller.domain.ProductionOption;
import jandy3.DesignSeller.domain.ProductionThumbnailImage;
import jandy3.DesignSeller.dto.ProductionListResponse;
import jandy3.DesignSeller.dto.ProductionResponse;
import jandy3.DesignSeller.service.ProductionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductionApiController {
    private final ProductionService productionService;

    @GetMapping(value ="/production/list")
    public Result getProductionList(Pageable pageable) {
        List<ProductionDto> collect = productionService.getPostListPage(pageable)
                .stream().map(
                        p -> {
                            String thumbnail = "";
                            if(p.getProductionThumbnailImage() != null)
                                thumbnail = p.getProductionThumbnailImage().getImageName();

                            return new ProductionDto(
                                    p.getId(), p.getName(),
                                    p.getCompany().getName(),
                                    thumbnail,
                                    p.getCategory().getName(),
                                    p.getLike()
                            );
                        }
                )
                .collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping(value = "/production/{productionId}")
    public ProductionResponse getProductionById(@PathVariable Long productionId) {
        productionService.updateView(productionId);
        return new ProductionResponse(true, productionService.findById(productionId));
    }

    @Data
    @AllArgsConstructor
    static class ProductionListResponse {
        private List<Production> productions;
    }

    @Data
    @AllArgsConstructor
    static class ProductionDto {
        private Long id;
        private String name;
        private String marketName;
        private String productionThumbnailImage;
        private String category;
        private int like;
    }

    @Data
    @AllArgsConstructor
    static class Result<T> {
        private T data;
    }
}
