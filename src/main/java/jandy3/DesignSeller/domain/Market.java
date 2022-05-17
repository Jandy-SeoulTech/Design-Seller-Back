package jandy3.DesignSeller.domain;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
public class Market {
    @Id
    @GeneratedValue
    @Column(name = "market_id")
    private Long id;

    private String name;
    private String description;
    private String marketImage;
    @CreationTimestamp
    private Timestamp createDate;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
}
