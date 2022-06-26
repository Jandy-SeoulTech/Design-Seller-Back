package jandy3.DesignSeller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jandy3.DesignSeller.domain.ItemStatus;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Schema
public class CreateItemDto {
    @Schema(name = "상품 이름")
    @NotEmpty
    private String name;
    @Schema(name = "상품 정보")
    @NotEmpty
    private String info;
    @Schema(name = "상품 해시태그")
    @NotEmpty
    private List<String> hashtag;
    @Schema(name = "상품 가격")
    @NotNull
    private int price;
    @Schema(name = "상품 썸네일 이미지")
    private List<String> itemThumbnailImages;
    @Schema(name = "상품 옵션")
    private List<ItemOptionDto> options;
    @Schema(name = "상품 상태(READY=0, SELL=1, SOLD_OUT=2)")
    @NotNull
    private ItemStatus itemStatus;
    @Schema(name = "환불 계좌")
    private AccountDto account;
    @Schema(name = "반품 주소")
    private ReturnAddressDto returnAddress;
    @Schema(name = "상품 설명 타이틀")
    private String title;
    @Schema(name = "상품 설명")
    private String description;
}


