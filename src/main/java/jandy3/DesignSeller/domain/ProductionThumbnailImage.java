package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
public class ProductionThumbnailImage {
    @Id
    @GeneratedValue
    @Column(name = "production_thumbnail_image_id")
    private String id;

    private String imageName;
}
