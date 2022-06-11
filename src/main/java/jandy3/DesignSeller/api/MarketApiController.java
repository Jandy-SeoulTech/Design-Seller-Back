package jandy3.DesignSeller.api;

import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.dto.IdResponse;
import jandy3.DesignSeller.oauth.annotation.CurrentUser;
import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.dto.MarketRequest;
import jandy3.DesignSeller.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MarketApiController {

    private final MarketService marketService;

    @PostMapping(value = "/market/new")
    @PreAuthorize("hasRole('ROLE_USER')")
    public IdResponse createMarket(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestBody MarketRequest marketRequest
    ) {

        //Long userId = principalDetails.getId();
        Market market = new Market();
        market.setUser(principalDetails.getUser());
        market.setName(marketRequest.getName());
        market.setDescription(marketRequest.getDescription());
        market.setMarketImage(marketRequest.getMarketImage());
        Long marketId = marketService.createMarket(market);

        return new IdResponse(true, marketId);
    }
}