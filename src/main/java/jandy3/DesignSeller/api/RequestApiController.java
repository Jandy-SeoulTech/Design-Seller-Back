package jandy3.DesignSeller.api;

import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.dto.AddressDto;
import jandy3.DesignSeller.dto.ProductionOptionInfo;
import jandy3.DesignSeller.dto.RequesterDto;
import jandy3.DesignSeller.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RequestApiController {
    private final RequestService requestService;

    @PostMapping(value = "/request/new")
    public CreateRequestResponse createRequest(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestBody CreateRequestInfo createRequestInfo) {
        Long requestId = requestService
                .createRequest(
                        principalDetails.getId(),
                        createRequestInfo.getOptions(),
                        createRequestInfo.getRequestFiles(),
                        createRequestInfo.getAddress(),
                        createRequestInfo.getRequester()
                );

        return new CreateRequestResponse(requestId);
    }

    @Data
    @AllArgsConstructor
    static class CreateRequestResponse {
        private Long id;
    }

    @Data
    @Getter
    static class CreateRequestInfo {
        private Long productionId;
        private List<ProductionOptionInfo> options;
        private List<String> requestFiles;
        private AddressDto address;
        private RequesterDto requester;
    }
}
