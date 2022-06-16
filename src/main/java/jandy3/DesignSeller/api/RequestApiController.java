package jandy3.DesignSeller.api;

import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.dto.*;
import jandy3.DesignSeller.repository.requestQuery.RequestQueryRepository;
import jandy3.DesignSeller.service.RequestService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RequestApiController {
    private final RequestService requestService;
    private final RequestQueryRepository requestQueryRepository;

    @GetMapping(value = "/request/list")
    public Result requestList() {
        List<RequestFlatDto> flats = requestQueryRepository.findByDto_flat();

        List<RequestQueryDto> collect = flats.stream()
                .collect(groupingBy(r -> new RequestQueryDto(r.getId(), r.getCreateDate(), r.getThumbnailImage(), r.getProductionId(), r.getProductionName(), r.getCompanyName(), r.getTotalPrice(), r.getRequestStatus()),
                    mapping(r -> new RequestOptionQueryDto(r.getOptionId(), r.getOptionName(), r.getOptionPrice(), r.getOptionCount()), toList())
                )).entrySet().stream()
                .map(e -> new RequestQueryDto(e.getKey().getId(), e.getKey().getCreateDate(), e.getKey().getThumbnailImage(), e.getKey().getProductionId(), e.getKey().getProductionName(), e.getKey().getCompanyName(), e.getKey().getTotalPrice(), e.getKey().getRequestStatus(), e.getValue()))
                .collect(toList());
        return new Result(collect);
    }

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
