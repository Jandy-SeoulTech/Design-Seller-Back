package jandy3.DesignSeller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@AllArgsConstructor
public class ItemOptionValueDto {
    private String optionValue;
    private int stockQuantity;
    private Boolean isRequire;
}
