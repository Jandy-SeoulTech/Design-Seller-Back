package jandy3.DesignSeller.dto;

import jandy3.DesignSeller.domain.RequestStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;

@Data
public class RequestFlatDto {
    private Long id;
    private Date createDate;
    private String thumbnailImage;
    private String companyName;
    private Long productionId;
    private String productionName;
    private Long optionId;
    private String optionName;
    private int optionPrice;
    private int optionCount;
    private int totalPrice;
    private RequestStatus requestStatus;

    public RequestFlatDto(Long id, Date createDate, String thumbnailImage, String companyName, Long productionId, String productionName, Long optionId, String optionName, int optionPrice, int optionCount, int totalPrice, RequestStatus requestStatus) {
        this.id = id;
        this.createDate = createDate;
        this.thumbnailImage = thumbnailImage;
        this.companyName = companyName;
        this.productionId = productionId;
        this.productionName = productionName;
        this.optionId = optionId;
        this.optionName = optionName;
        this.optionPrice = optionPrice;
        this.optionCount = optionCount;
        this.totalPrice = totalPrice;
        this.requestStatus = requestStatus;
    }
}
