package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.dto.Result;
import jandy3.DesignSeller.repository.MarketRepository;
import jandy3.DesignSeller.service.MarketService;
import lombok.*;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.stream.Collectors;

@Api(tags = {"마켓 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MarketApiController {

    private final MarketService marketService;
    private final MarketRepository marketRepository;

    @ApiOperation(value = "마켓 생성")
    @PostMapping(value = "/market/new")
    public CreateMarketResponse createMarket(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestBody @Valid CreateMarketRequest createMarketRequest
    ) {

        Long userId = principalDetails.getId();
        Market market = new Market();
        market.setUser(principalDetails.getUser());
        market.setName(createMarketRequest.getName());
        market.setDescription(createMarketRequest.getDescription());
        market.setMarketImage(createMarketRequest.getMarketImage());
        Long marketId = marketService.createMarket(market);

        return new CreateMarketResponse(marketId);
    }

    @ApiOperation(value = "마켓 조회")
    @GetMapping(value = "/market/list")
    public Result getMarketList(
            @ApiParam(value = "예시: {ip}:8080/api/v1/market/list?page=0&size=4&sort=like,DESC")
                    Pageable pageable
    ) {
        List<MarketDto> collect = marketService.findAll(pageable).stream()
                .map(m -> new MarketDto(m.getId(), m.getName(), m.getLike(), m.getMarketImage(), m.getDescription()))
                .collect(Collectors.toList());
        return new Result(collect);
    }

    @ApiOperation(value = "마켓 이름 중복확인")
    @GetMapping(value = "/market/check")
    public CheckDto checkMarketName(
            @ApiParam(value = "마켓 이름")
            @RequestParam String name
    ) {
        try {
            marketRepository.findByMarketName(name);
        } catch(NoResultException e) {
            return new CheckDto(true);
        }
        return new CheckDto(false);
    }

    @Schema(name = "사용가능여부")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class CheckDto {
        private boolean available;
    }

    @Schema(name = "마켓 생성 응답")
    @AllArgsConstructor
    @Data
    static class CreateMarketResponse {
        @Schema(name = "생성된 마켓의 id")
        private Long id;
    }

    @Schema(name = "마켓 생성 요청")
    @Data
    static class CreateMarketRequest {
        @Schema(name = "마켓 이름")
        @NotEmpty
        private String name;

        @Schema(name = "마켓 설명")
        @NotEmpty
        private String description;
        @Schema(name = "마켓 이미지 이름")
        private String marketImage;
    }

    @Schema(name = "마켓 리스트 조회 아이템")
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    static class MarketDto {
        @Schema(name = "마켓 ID")
        private Long id;
        @Schema(name = "마켓 이름")
        private String name;
        @Schema(name = "마켓 좋아요")
        private int like;
        @Schema(name = "마켓 이미지")
        private String marketImage;
        @Schema(name = "마켓 설명")
        private String description;
    }
}