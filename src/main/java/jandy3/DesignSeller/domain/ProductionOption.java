package jandy3.DesignSeller.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
public class ProductionOption {
    @Id
    @GeneratedValue
    @Column(name = "production_option_id")
    private String id;

    private String name;

    private Integer price;

    @ManyToOne
    @JoinColumn(name = "production_id")
    @JsonIgnore
    private Production production;
}