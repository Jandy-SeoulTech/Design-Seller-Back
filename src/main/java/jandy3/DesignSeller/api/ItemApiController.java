package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.domain.*;
import jandy3.DesignSeller.domain.embed.Account;
import jandy3.DesignSeller.domain.embed.ReturnAddress;
import jandy3.DesignSeller.dto.CreateItemDto;
import jandy3.DesignSeller.service.HashtagService;
import jandy3.DesignSeller.service.ItemService;
import jandy3.DesignSeller.service.MarketService;
import jandy3.DesignSeller.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @PostMapping("/item/new")
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

    @Data
    @AllArgsConstructor
    static class CreateItemResponse {
        private Long id;
    }
}
