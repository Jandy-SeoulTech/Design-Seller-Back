package jandy3.DesignSeller.dto;

import jandy3.DesignSeller.domain.Production;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ProductionResponse {
    private boolean success;
    private Production production;
}
