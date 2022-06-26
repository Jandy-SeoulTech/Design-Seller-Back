package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Hashtag {
    @GeneratedValue
    @Id
    @Column(name = "hashtag_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "hashtag")
    private List<HashtagItem> hashtagItems = new ArrayList<>();

    //== 연관관계 메서드 ==//
    public void addHashtagItem(HashtagItem hashtagItem) {
        hashtagItems.add(hashtagItem);
        hashtagItem.setHashtag(this);
    }

    //== 생성 메서드 ==//
    public static Hashtag createHashtag(String name) {
        return new Hashtag(name);
    }

    //== constructor ==//
    private Hashtag() {}
    private Hashtag(String name) {
        this.name = name;
    }
}