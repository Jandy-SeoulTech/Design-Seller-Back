package jandy3.DesignSeller.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class RequestInfo {
    private Long productionId;
    private List<ProductionOptionInfo> options;
    private List<String> requestFiles;
}
