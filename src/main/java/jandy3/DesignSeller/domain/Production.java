package jandy3.DesignSeller.domain;

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

    private String name;

    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String thumbnailImage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "production")
    private List<ProductionImage> productionImages = new ArrayList<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "production")
    private List<ProductionOption> productionOptions = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(columnDefinition = "integer default 0", nullable = false, name = "likes")
    private Integer like;

    @Column(columnDefinition = "integer default 0", nullable = false)
    private Integer view;

    @CreationTimestamp
    private Timestamp createDate;

    @UpdateTimestamp
    private Timestamp updateDate;
}