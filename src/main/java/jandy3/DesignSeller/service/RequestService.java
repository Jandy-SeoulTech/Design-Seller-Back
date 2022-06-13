package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.*;
import jandy3.DesignSeller.dto.ProductionOptionInfo;
import jandy3.DesignSeller.exception.ResourceNotFoundException;
import jandy3.DesignSeller.repository.ProductionOptionRepository;
import jandy3.DesignSeller.repository.ProductionRepository;
import jandy3.DesignSeller.repository.RequestRepository;
import jandy3.DesignSeller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RequestService {
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;
    private final ProductionRepository productionRepository;
    private final ProductionOptionRepository productionOptionRepository;
    /**
     * 주문
     */
    @Transactional
    public Long createRequest(Long userId, List<ProductionOptionInfo> productionOptionInfos, List<String> requestFilenames) {
        // 유저 조회
        User user = userRepository.findOne(userId);
        // 제작 조회
        List<ProductionRequest> productionRequests = new ArrayList<>(); // ProductionRequest 리스트 생성
        for (ProductionOptionInfo productionOptionInfo : productionOptionInfos) {
            Long productionOptionId = productionOptionInfo.getProductionOptionId();
            ProductionOption productionOption = productionOptionRepository.findById(productionOptionId)
                    .orElseThrow(() -> new ResourceNotFoundException("ProductionOption", "id", productionOptionId));
            productionRequests.add(
                    ProductionRequest.createProductionRequest(
                            productionOption, productionOptionInfo.getCount()
                    ));
        }
        // 제작 파일 추가
        List<RequestFile> requestFiles = new ArrayList<>();
        for (String requestFilename : requestFilenames) {
            requestFiles.add(RequestFile.createRequestFile(requestFilename));
        }
        return requestRepository.save(Request.createRequest(user, productionRequests, requestFiles)).getId();
    }
}
