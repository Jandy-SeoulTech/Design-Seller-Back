package jandy3.DesignSeller.api;

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

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryApiController {

    private final CategoryService categoryService;

    @GetMapping(value = "/categories")
    public Result getCategories() {
        List<Category> categories = categoryService.findParentCategories();
        List<CategoryDto> collect = categories.stream()
                .map(c -> new CategoryDto(c.getId(),c.getName(), c.getChild()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @Data
    static class CategoryDto {
        private Long id;
        private String name;
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
