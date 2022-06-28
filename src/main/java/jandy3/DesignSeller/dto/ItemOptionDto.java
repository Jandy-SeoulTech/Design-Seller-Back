package jandy3.DesignSeller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemOptionDto {
        private String optionName;
        private List<ItemOptionValueDto> optionValues;
}
