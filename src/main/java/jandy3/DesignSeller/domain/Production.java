package jandy3.DesignSeller.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Production {
    @Id
    @GeneratedValue
    @Column(name = "production_id")
    private Long id;

    @OneToMany(cascade = CascadeType.ALL)
    private List<ProductionImage> productionImages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "production")
    private List<ProductionOption> productionOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonIgnore
    private Category category;

    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view;
}