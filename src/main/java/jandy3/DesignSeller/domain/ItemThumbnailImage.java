package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ItemThumbnailImage {
    @GeneratedValue
    @Id
    @Column(name = "item_thumbnail_image_id")
    private Long id;

    private String imageName;

    @ManyToOne(fetch = FetchType.LAZY)
    private Item item;

    //== 연관관계 편의 메서드 ==//
    public void setItem(Item item) {
        this.item = item;
    }

    //== constructor ==//
    private ItemThumbnailImage() {}
    private ItemThumbnailImage(String imageName) {
        this.imageName = imageName;
    }

    //== 생성 메서드 ==//
    public static ItemThumbnailImage createItemThumbnailImage(String imageName) {
        return new ItemThumbnailImage(imageName);
    }
}
