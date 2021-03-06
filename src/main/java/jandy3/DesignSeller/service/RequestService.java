package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.*;
import jandy3.DesignSeller.dto.AddressDto;
import jandy3.DesignSeller.dto.ProductionOptionInfo;
import jandy3.DesignSeller.dto.RequesterDto;
import jandy3.DesignSeller.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ProductionRepository productionRepository;
    private final ProductionOptionRepository productionOptionRepository;
    private final MarketService marketService;
    /**
     * 주문
     */
    @Transactional
    public Long createRequest(Long userId, List<ProductionOptionInfo> productionOptionInfos, List<String> requestFilenames, AddressDto addressDto, RequesterDto requesterDto) {
        // 유저 조회
        User user = userRepository.findOne(userId);
        // 마켓 조회
        Market market = marketService.findByUserId(userId);
        // 제작 조회

        int totalPrice = 0;
        List<ProductionRequest> productionRequests = new ArrayList<>(); // ProductionRequest 리스트 생성
        for (ProductionOptionInfo productionOptionInfo : productionOptionInfos) {
            Long productionOptionId = productionOptionInfo.getProductionOptionId();
            ProductionOption productionOption = productionOptionRepository.findOne(productionOptionId);
            productionRequests.add(
                    ProductionRequest.createProductionRequest(
                            productionOption, productionOptionInfo.getCount()
                    ));
            totalPrice += productionOption.getPrice() * productionOptionInfo.getCount();
        }

        // 제작 파일 추가
        List<RequestFile> requestFiles = new ArrayList<>();
        for (String requestFilename : requestFilenames) {
            requestFiles.add(RequestFile.createRequestFile(requestFilename));
        }
        Request request = Request.createRequest(market, productionRequests, requestFiles);

        // 배송지 지정
        request.setAddress(addressDto.getStreet(), addressDto.getZipcode(), addressDto.getDetail());

        // 총액 지정
        request.setTotalPrice(totalPrice);
        // 의뢰자 지정
        request.setRequester(requesterDto.getName(), requesterDto.getPhone(), requesterDto.getEmail());
        requestRepository.save(request);
        return request.getId();
    }
}
