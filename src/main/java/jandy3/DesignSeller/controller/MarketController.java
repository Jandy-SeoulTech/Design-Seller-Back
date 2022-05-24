package jandy3.DesignSeller.controller;

import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.oauth.annotation.CurrentUser;
import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.dto.MarketRequest;
import jandy3.DesignSeller.paylaod.ApiResponse;
import jandy3.DesignSeller.service.MarketService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MarketController {

    private final MarketService marketService;

    @PostMapping(value = "/market/new")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ApiResponse createMarket(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestBody MarketRequest marketRequest
    ) {

        //Long userId = principalDetails.getId();
        Market market = new Market();
        market.setUser(principalDetails.getUser());
        market.setName(marketRequest.getName());
        market.setDescription(marketRequest.getDescription());
        market.setMarketImage(marketRequest.getMarketImage());
        marketService.createMarket(market);
        return new ApiResponse(true, "Create Market");
    }
}