package jandy3.DesignSeller.domain;

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
@Setter
public class Market {
    @Id
    @GeneratedValue
    @Column(name = "market_id")
    private Long id;

    private String name;
    @Column(columnDefinition = "TEXT")
    private String description;
    private String marketImage;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "market")
    private List<Request> requests = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;
}
