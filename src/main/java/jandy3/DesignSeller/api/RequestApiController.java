package jandy3.DesignSeller.api;

import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.dto.IdResponse;
import jandy3.DesignSeller.dto.RequestInfo;
import jandy3.DesignSeller.oauth.annotation.CurrentUser;
import jandy3.DesignSeller.service.RequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class RequestApiController {
    private final RequestService requestService;

    @PostMapping(value = "/request/new")
    public IdResponse createRequest(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestBody RequestInfo requestInfo) {
        Long requestId = requestService
                .createRequest(principalDetails.getId(), requestInfo.getOptions(), requestInfo.getRequestFiles());
        return new IdResponse(true, requestId);
    }

}
