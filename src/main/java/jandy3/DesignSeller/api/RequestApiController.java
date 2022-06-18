package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.media.Schema;
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

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.List;

import static java.util.stream.Collectors.*;

@Api(tags = {"제작 요청 API"})
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class RequestApiController {
    private final RequestService requestService;
    private final RequestQueryRepository requestQueryRepository;

    @ApiOperation(value = "제작 의로 리스트 조회")
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

    @ApiOperation(value = "제작 의뢰 생성")
    @PostMapping(value = "/request/new")
    public CreateRequestResponse createRequest(
            @CurrentUser PrincipalDetails principalDetails,
            @RequestBody @Valid CreateRequestInfo createRequestInfo) {

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
    @Schema
    static class CreateRequestInfo {
        @Schema(name = "제작 상품 id")
        @NotEmpty
        private Long productionId;
        @Schema(name = "제작 상품 옵션")
        @NotEmpty
        private List<ProductionOptionInfo> options;
        @Schema(name = "첨부 파일")
        private List<String> requestFiles;
        @Schema(name = "배송지 주소")
        @NotNull
        private AddressDto address;
        @Schema(name = "의뢰자 정보")
        @NotNull
        private RequesterDto requester;
    }
}
