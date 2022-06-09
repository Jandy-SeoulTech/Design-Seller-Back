package jandy3.DesignSeller.dto;

import jandy3.DesignSeller.domain.Production;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ProductionListResponse {
    private boolean success;
    private List<Production> productions;
}
