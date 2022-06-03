package jandy3.DesignSeller.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
public class ProductionRequest {
    @Id
    @GeneratedValue
    @Column(name = "production_request_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_id")
    @Setter
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_option_id")
    @Setter
    private ProductionOption productionOption;

    @Setter
    private int requestPrice;
    @Setter
    private int count;

    //==비즈니스 로직==//
    public void cancel() {
    }

    public int getTotalPrice() {
        return getRequestPrice() * getCount();
    }

    //==생성 메서드==//
    public static ProductionRequest createProductionRequest(ProductionOption productionOption, int requestPrice, int count) {
        ProductionRequest productionRequest = new ProductionRequest();
        productionRequest.setProductionOption(productionOption);
        productionRequest.setRequestPrice(requestPrice);
        productionRequest.setCount(count);
        return productionRequest;

    }
}
