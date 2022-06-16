package jandy3.DesignSeller.api;

import jandy3.DesignSeller.domain.Production;
import jandy3.DesignSeller.dto.Result;
import jandy3.DesignSeller.service.ProductionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductionApiController {
    private final ProductionService productionService;

    @GetMapping(value = "/production/list")
    public Result getProductionList(Pageable pageable) {
        List<ProductionDto> collect = productionService.getPostListPage(pageable)
                .stream().map(
                        p -> new ProductionDto(
                                p.getId(),
                                p.getName(),
                                p.getCompany().getName(),
                                p.getThumbnailImage(),
                                p.getCategory().getName(),
                                p.getLike(),
                                p.getView())
                ).collect(Collectors.toList());

        return new Result(collect);
    }

    @GetMapping(value = "/production/{productionId}")
    public ProductionDetailResponse getProductionById(@PathVariable Long productionId) {
        productionService.updateView(productionId);
        Production production = productionService.findById(productionId);

        List<String> images = production.getProductionImages().stream()
                .map(i -> i.getImageName())
                .collect(Collectors.toList());

        List<ProductionOptionDto> options = production.getProductionOptions().stream()
                .map(o -> new ProductionOptionDto(o.getName(), o.getPrice()))
                .collect(Collectors.toList());

        return new ProductionDetailResponse(
                productionId,
                production.getName(),
                production.getCompany().getName(),
                production.getThumbnailImage(),
                images,
                production.getDescription(),
                production.getCategory().getName(),
                production.getLike(),
                production.getView(),
                options
        );
    }

    @Data
    @AllArgsConstructor
    static class ProductionDetailResponse {
        private Long id;
        private String name;
        private String company;
        private String productionThumbnailImage;
        private List<String> productionImages;
        private String description;
        private String category;
        private int like;
        private int view;
        private List<ProductionOptionDto> options;
    }

    @Data
    @AllArgsConstructor
    static class ProductionOptionDto {
        private String name;
        private Integer price;
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
        private String company;
        private String productionThumbnailImage;
        private String category;
        private int like;
        private int view;
    }
}
