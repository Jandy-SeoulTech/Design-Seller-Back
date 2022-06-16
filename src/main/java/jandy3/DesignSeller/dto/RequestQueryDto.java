package jandy3.DesignSeller.dto;

import jandy3.DesignSeller.domain.RequestStatus;
import lombok.Data;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Data
public class RequestQueryDto {
    private Long id;
    private Date createDate;
    private String thumbnailImage;
    private Long productionId;
    private String productionName;
    private List<RequestOptionQueryDto> options;
    private String companyName;
    private int totalPrice;
    private RequestStatus requestStatus;

    public RequestQueryDto(Long id, Date createDate, String thumbnailImage, Long productionId, String productionName, String companyName, int totalPrice, RequestStatus requestStatus) {
        this.id = id;
        this.createDate = createDate;
        this.thumbnailImage = thumbnailImage;
        this.productionId = productionId;
        this.productionName = productionName;
        this.companyName = companyName;
        this.totalPrice = totalPrice;
        this.requestStatus = requestStatus;
    }
    public RequestQueryDto(Long id, Date createDate, String thumbnailImage, Long productionId, String productionName, String companyName, int totalPrice, RequestStatus requestStatus, List<RequestOptionQueryDto> options) {
        this.id = id;
        this.createDate = createDate;
        this.thumbnailImage = thumbnailImage;
        this.productionId = productionId;
        this.productionName = productionName;
        this.options = options;
        this.companyName = companyName;
        this.totalPrice = totalPrice;
        this.requestStatus = requestStatus;
    }
}
