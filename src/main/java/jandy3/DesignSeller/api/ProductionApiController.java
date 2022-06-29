package jandy3.DesignSeller.api;

import io.swagger.annotations.*;
import jandy3.DesignSeller.domain.Production;
import jandy3.DesignSeller.dto.Result;
import jandy3.DesignSeller.service.ProductionService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"제작 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ProductionApiController {
    private final ProductionService productionService;

    @ApiOperation(value = "제작 리스트 조회")
    @GetMapping(value = "/production/list")
    public Result getProductionList(
            @RequestParam(required = false) Long categoryId,
            @ApiParam(value = "예시: {ip}:8080/production/list?page=0&size=5&sort=view,DESC")
            Pageable pageable
    ) {
        List<ProductionDto> collect = productionService.getProductionList(categoryId, pageable)
                .stream().map(
                        p -> {
                            int price = p.getProductionOptions().size()>0 ? p.getProductionOptions().get(0).getPrice() : 0;
                            return new ProductionDto(
                                    p.getId(),
                                    p.getName(),
                                    price,
                                    p.getCompany().getName(),
                                    p.getThumbnailImage(),
                                    p.getCategory().getName(),
                                    p.getLike(),
                                    p.getView());
                        }
                ).collect(Collectors.toList());

        return new Result(collect);
    }

    @ApiOperation(value = "제작 상세 조회")
    @GetMapping(value = "/production/{productionId}")
    public ProductionDetailResponse getProductionById(
            @ApiParam(value = "해당 id를 가진 제작 상세 조회")
            @PathVariable Long productionId
    ) {
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
        private int price;
        private String company;
        private String productionThumbnailImage;
        private String category;
        private int like;
        private int view;
    }
}
