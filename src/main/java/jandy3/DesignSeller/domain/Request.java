package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Request {
    @Id
    @GeneratedValue
    @Column(name = "requset_id")
    private Long id;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "request")
    private List<ProductionRequest> productionRequests = new ArrayList<>();

    private RequestStatus status;

    //== 연관관계 편의 메서드 ==//
    public void setUser(User user) {
        this.user = user;
        user.getRequests().add(this);
    }

    public void addProductionRequest(ProductionRequest productionRequest) {
        productionRequests.add(productionRequest);
        productionRequest.setRequest(this);
    }

    //== 생성 메서드 ==//

    public static Request createRequest(User user, ProductionRequest... productionRequests) {
        Request request = new Request();
        request.setUser(user);
        for (ProductionRequest productionRequest : productionRequests) {
            request.addProductionRequest(productionRequest);
        }
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
