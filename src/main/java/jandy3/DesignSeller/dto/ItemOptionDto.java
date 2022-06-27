package jandy3.DesignSeller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ItemOptionDto {
        private String optionName;
        private List<ItemOptionValueDto> optionValues;
}
