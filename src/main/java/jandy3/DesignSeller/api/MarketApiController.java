package jandy3.DesignSeller.api;

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

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MarketApiController {

    private final MarketService marketService;

    @PostMapping(value = "/market/new")
    @PreAuthorize("hasRole('ROLE_USER')")
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

    @AllArgsConstructor
    @Data
    static class CreateMarketResponse {
        private Long id;
    }

    @Data
    static class CreateMarketRequest {
        @NotEmpty
        private String name;
        @NotEmpty
        private String description;
        private String marketImage;
    }
}