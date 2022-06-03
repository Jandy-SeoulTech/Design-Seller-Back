package jandy3.DesignSeller.domain;

import lombok.Getter;

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
    private Request request;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "production_option_id")
    private ProductionOption productionOption;

    private Integer count;
}
