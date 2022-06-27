package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.domain.*;
import jandy3.DesignSeller.domain.embed.Account;
import jandy3.DesignSeller.domain.embed.ReturnAddress;
import jandy3.DesignSeller.dto.CreateItemDto;
import jandy3.DesignSeller.dto.ItemOptionDto;
import jandy3.DesignSeller.dto.ItemOptionValueDto;
import jandy3.DesignSeller.dto.Result;
import jandy3.DesignSeller.service.HashtagService;
import jandy3.DesignSeller.service.ItemService;
import jandy3.DesignSeller.service.MarketService;
import jandy3.DesignSeller.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"쇼핑몰 상품 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class ItemApiController {
    private final ItemService itemService;
    private final UserService userService;
    private final MarketService marketService;
    private final HashtagService hashtagService;

    @ApiOperation(value = "쇼핑몰 상품 생성")
    @PostMapping(value = "/item/new")
    public CreateItemResponse createItem(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestBody @Valid CreateItemDto createItemDto
    ) {
        Long userId = principalDetails.getId();
        // 유저 조회
        User user = userService.findOne(userId);
        // 마켓 조회
        Market market = marketService.findByUserId(userId);

        // 옵션 생성
        List<ItemOption> itemOptions = createItemDto.getOptions().stream().map(iod -> {
            List<ItemOptionValue> itemOptionValues = iod.getOptionValues().stream()
                    .map(ov -> ItemOptionValue.createItemOptionValue(ov.getOptionValue(), ov.getStockQuantity(), ov.getIsRequire()))
                    .collect(Collectors.toList());
            return ItemOption.createItemOption(iod.getOptionName(), itemOptionValues);
        }).collect(Collectors.toList());

        // 썸네일 이미지
        List<ItemThumbnailImage> itemThumbnailImages = createItemDto.getItemThumbnailImages().stream()
                .map(ti -> ItemThumbnailImage.createItemThumbnailImage(ti))
                .collect(Collectors.toList());

        // 해시태그 조회
        List<Hashtag> hashtags = createItemDto.getHashtag().stream().map(h -> hashtagService.createOrGetHashtag(h))
                .collect(Collectors.toList());

        /**
         * 해쉬태그가 존재하지 않을 때 예외처리 필요
         */

        // 해시태그 등록
        List<HashtagItem> hashtagItems = hashtags.stream().map(h -> HashtagItem.createHashtagItem(h))
                .collect(Collectors.toList());

        // 반품주소 생성
        ReturnAddress returnAddress = new ReturnAddress(createItemDto.getReturnAddress().getName(), createItemDto.getReturnAddress().getAddress());

        // 환불계좌 생성
        Account returnAccount = new Account(createItemDto.getAccount().getBank(), createItemDto.getAccount().getAccountNumber());

        Item item = Item.createItem(
                market, itemOptions, itemThumbnailImages, hashtagItems,
                createItemDto.getPrice(), createItemDto.getName(), createItemDto.getTitle(),
                createItemDto.getDescription(), createItemDto.getInfo(), createItemDto.getItemStatus()
                , returnAddress, returnAccount
        );
        itemService.createItem(item);
        return new CreateItemResponse(item.getId());
    }

    @GetMapping(value = "/item/list")
    public Result getItemList(Pageable pageable) {
        List<Item> items = itemService.findAll(pageable);

        List<ItemDto> collect = items.stream().map(
                i -> {
                    return new ItemDto(i.getId(), i.getMarket().getName(), i.getName(), i.getPrice(), i.getLike());
                }
        ).collect(Collectors.toList());
        return new Result(collect);
    }

    @GetMapping(value = "/item/{itemId}")
    public ItemDetailResponse getItemDetail(@PathVariable Long itemId) {
        Item item = itemService.findOne(itemId);
        List<String> itemThumbnailImages = item.getItemThumbnailImages().stream()
                .map(ti -> ti.getImageName())
                .collect(Collectors.toList());
        List<ItemOptionDto> itemOptions = item.getItemOptions().stream()
                .map(io -> {
                    List<ItemOptionValueDto> itemOptionValues = io.getItemOptionDetails().stream()
                            .map(iod -> new ItemOptionValueDto(
                                    iod.getName(),
                                    iod.getStockQuantity(),
                                    iod.getIsRequire()
                            )).collect(Collectors.toList());
                    return new ItemOptionDto(io.getName(), itemOptionValues);
                }).collect(Collectors.toList());
        return new ItemDetailResponse(
                item.getId(),
                item.getName(),
                itemOptions,
                item.getMarket().getName(),
                itemThumbnailImages,
                item.getTitle(),
                item.getInfo(),
                item.getDescription(),
                item.getItemStatus()
        );
    }

    @Data
    @AllArgsConstructor
    static class ItemDetailResponse {
        private Long id;
        private String name;
        private List<ItemOptionDto> itemOptions;
        private String marketName;
        private List<String> itemThumbnailImages;
        private String title;
        private String info;
        private String description;
        private ItemStatus itemStatus;
    }

    @Data
    @AllArgsConstructor
    static class ItemDto {
        private Long id;
        private String marketName;
        private String name;
        private int price;
        private int like;
    }

    @Data
    @AllArgsConstructor
    static class CreateItemResponse {
        private Long id;
    }
}
