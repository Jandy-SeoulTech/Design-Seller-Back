package jandy3.DesignSeller.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ProductionImage {

    @Id
    @GeneratedValue
    @Column(name = "production_image_name")
    private String id;

    private String imageName;
}