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
public class Company {
    @Id
    @GeneratedValue
    @Column(name = "company_id")
    private Long id;

    private String name;

    @OneToMany()
    @JoinColumn(name = "company_id")
    private List<Production> productions = new ArrayList<>();

    @CreationTimestamp
    private Timestamp createDate;
    @UpdateTimestamp
    private Timestamp updateDate;
}
