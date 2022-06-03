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
}
