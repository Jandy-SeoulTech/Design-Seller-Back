package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jandy3.DesignSeller.domain.Category;
import jandy3.DesignSeller.dto.Result;
import jandy3.DesignSeller.service.CategoryService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"카테고리 API"})
@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping(value = "/categories")
    @Operation(summary = "카테고리 조회")
    public Result getCategories() {
        List<Category> categories = categoryService.findParentCategories();
        List<CategoryDto> collect = categories.stream()
                .map(c -> new CategoryDto(c.getId(),c.getName(), c.getChild()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    @Schema(description = "카테고리")
    static class CategoryDto {
        private Long id;
        @Schema(description = "카테고리 명")
        private String name;
        @Schema(description = "하위 카테고리")
        private List<CategoryDto> subcategory;
        public CategoryDto(Long id, String name, List<Category> child) {
            this.id = id;
            this.name = name;
            if(child != null) {
                subcategory = child.stream()
                        .map(c -> new CategoryDto(c.getId(), c.getName(), null))
                        .collect(Collectors.toList());
            }
        }
    }
}
