package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class CategoryItem {
    @Id
    @GeneratedValue
    @Column(name = "category_item_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
}