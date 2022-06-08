package jandy3.DesignSeller.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Request {
    @Id
    @GeneratedValue
    @Column(name = "request_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<ProductionRequest> productionRequests = new ArrayList<>();

    @Setter
    private RequestStatus status;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<RequestFile> requestFiles = new ArrayList<>();

    //== 연관관계 편의 메서드 ==//
    public void setUser(User user) {
        this.user = user;
        user.getRequests().add(this);
    }

    public void addProductionRequest(ProductionRequest productionRequest) {
        productionRequests.add(productionRequest);
        productionRequest.setRequest(this);
    }

    public void addRequestFile(RequestFile requestFile) {
        requestFiles.add(requestFile);
        requestFile.setRequest(this);
    }
    //== 생성 메서드 ==//

    public static Request createRequest(User user, List<ProductionRequest> productionRequests, List<RequestFile> requestFiles) {
        Request request = new Request();
        request.setUser(user);
        for (ProductionRequest productionRequest : productionRequests) {
            request.addProductionRequest(productionRequest);
        }
        for(RequestFile requestFile : requestFiles) {
            request.addRequestFile(requestFile);
        }
        request.setStatus(RequestStatus.REQUEST);
        return request;
    }
    //== 비즈니스 로직 ==//
    /**
     * 의뢰 취소
     */

    public void cancel() {

        for(ProductionRequest productionRequest : productionRequests) {
            productionRequest.cancel();
        }
    }
    //==조회 로직==//
    /**
     * 전체 가격 조회
     */
    public int getTotalPrice() {
        return productionRequests.stream().mapToInt(ProductionRequest::getTotalPrice).sum();
    }

}
