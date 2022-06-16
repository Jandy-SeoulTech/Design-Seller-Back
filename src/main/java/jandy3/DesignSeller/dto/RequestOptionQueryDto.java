package jandy3.DesignSeller.dto;

import lombok.Data;

@Data
public class RequestOptionQueryDto {
    private Long optionId;
    private String optionName;
    private int price;
    private int count;

    public RequestOptionQueryDto(Long optionId, String optionName, int price, int count) {
        this.optionId = optionId;
        this.optionName = optionName;
        this.price = price;
        this.count = count;
    }
}
