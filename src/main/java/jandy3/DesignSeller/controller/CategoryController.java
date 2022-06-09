package jandy3.DesignSeller.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jandy3.DesignSeller.dto.Categories;
import jandy3.DesignSeller.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final ObjectMapper mapper;

    private final CategoryService categoryService;

    @GetMapping(value = "/categories")
    public Categories getCategories() throws JsonProcessingException {
        Categories response = new Categories();
        response.setSuccess(true);
        response.setResult(categoryService.findParents());
        return response;
    }
}
