package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class HashtagItem {
    @GeneratedValue
    @Id
    @Column(name = "hashtag_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hashtag_id")
    private Hashtag hashtag;

    //== 연관관계 편의 메서드 ==//
    public void setItem(Item item) {
        this.item = item;
    }

    public void setHashtag(Hashtag hashtag) {
        this.item = item;
    }

    //== 생성 메서드 ==//
    public static HashtagItem createHashtagItem(Hashtag hashtag) {
        HashtagItem hashtagItem = new HashtagItem();
        hashtag.addHashtagItem(hashtagItem);
        return hashtagItem;
    }
}
