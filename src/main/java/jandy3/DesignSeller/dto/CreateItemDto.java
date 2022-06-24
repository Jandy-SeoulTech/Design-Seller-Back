package jandy3.DesignSeller.dto;

import jandy3.DesignSeller.domain.ItemStatus;
import lombok.Data;

import java.util.List;

@Data
public class CreateItemDto {
    private String name;
    private String info;
    private List<String> hashtag;
    private int price;
    private List<String> itemThumbnailImages;
    private List<ItemOptionDto> options;
    private ItemStatus itemStatus;
    private AccountDto account;
    private ReturnAddressDto returnAddress;
    private String title;
    private String description;
}


