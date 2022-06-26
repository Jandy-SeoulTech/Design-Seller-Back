package jandy3.DesignSeller.dto;

import lombok.Data;

import java.util.List;

@Data
public class ItemOptionDto {
        private String optionName;
        private List<ItemOptionValueDto> optionValues;
}
