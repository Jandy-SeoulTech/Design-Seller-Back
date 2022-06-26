package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.service.MarketService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

@Api(tags = {"마켓 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MarketApiController {

    private final MarketService marketService;

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
}