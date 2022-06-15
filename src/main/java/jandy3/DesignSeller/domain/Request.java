package jandy3.DesignSeller.domain;

import jandy3.DesignSeller.domain.embed.Address;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
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
    @JoinColumn(name = "market_id")
    private Market market;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<ProductionRequest> productionRequests = new ArrayList<>();

    @Setter
    private RequestStatus status;

    @OneToMany(mappedBy = "request", cascade = CascadeType.ALL)
    private List<RequestFile> requestFiles = new ArrayList<>();

    private String requesterName;

    private String phone;

    private String email;

    @Embedded
    private Address address;

    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;

    //== 연관관계 편의 메서드 ==//
    public void setMarket(Market market) {
        this.market = market;
        market.getRequests().add(this);
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

    public static Request createRequest(Market market, List<ProductionRequest> productionRequests, List<RequestFile> requestFiles) {
        Request request = new Request();
        request.setMarket(market);
        for (ProductionRequest productionRequest : productionRequests) {
            request.addProductionRequest(productionRequest);
        }
        for(RequestFile requestFile : requestFiles) {
            request.addRequestFile(requestFile);
        }
        request.setStatus(RequestStatus.UNCHECKED);
        return request;
    }

    public void setAddress(String street, String zipcode, String detail) {
        street = street;
        zipcode = zipcode;
        detail = detail;
    }

    public void setRequester(String requesterName, String phone, String email) {
        requesterName = requesterName;
        phone = phone;
        email = email;
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
