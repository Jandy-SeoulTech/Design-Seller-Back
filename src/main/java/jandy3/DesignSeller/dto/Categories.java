package jandy3.DesignSeller.dto;

import jandy3.DesignSeller.domain.Category;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class Categories {
    private boolean success;
    private List<Category> result;
    public Categories() {
        result = new ArrayList<>();
    }
}
