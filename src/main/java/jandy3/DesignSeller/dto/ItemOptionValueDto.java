package jandy3.DesignSeller.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class ItemOptionValueDto {
    private String optionValue;
    private int stockQuantity;
    private Boolean isRequire;
}
