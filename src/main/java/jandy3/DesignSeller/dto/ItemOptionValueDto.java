package jandy3.DesignSeller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemOptionValueDto {
    private String optionValue;
    private int stockQuantity;
    private Boolean isRequire;
}
