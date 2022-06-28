package jandy3.DesignSeller.domain;

import jandy3.DesignSeller.domain.embed.Account;
import jandy3.DesignSeller.domain.embed.ReturnAddress;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private int price;

    private String name;

    private String info;

    private String title;

    private String description;

    @CreationTimestamp
    private Timestamp createDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "market_id")
    private Market market;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<HashtagItem> hashtagItems = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemThumbnailImage> itemThumbnailImages = new ArrayList<>();

    private ItemStatus itemStatus;

    @Embedded
    private ReturnAddress returnAddress;

    @Embedded
    private Account returnAccount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL)
    private List<ItemOption> itemOptions = new ArrayList<>();

    @Column(columnDefinition = "integer default 0", nullable = false)
    private int view;

    @Column(columnDefinition = "integer default 0", nullable = false, name = "likes")
    private int like;

    //== constructor ==//
    private Item() {}

    private Item(int price, String name, String title, String description, String info, ItemStatus itemStatus, ReturnAddress returnAddress, Account returnAccount) {
        this.price = price;
        this.name = name;
        this.title = title;
        this.description = description;
        this.info = info;
        this.itemStatus = itemStatus;
        this.returnAddress = returnAddress;
        this.returnAccount = returnAccount;
    }

    //== 생성 메서드 ==//
    public static Item createItem(Market market, List<ItemOption> itemOptions, List<ItemThumbnailImage> itemThumbnailImages, List<HashtagItem> hashtagItems,
                                  int price, String name, String title, String description, String info, ItemStatus itemStatus, ReturnAddress returnAddress, Account returnAccount) {
        Item item = new Item(price, name, title, description, info, itemStatus, returnAddress, returnAccount);

        // 마켓 지정 & 마켓에 아이템 추가
        item.setMarket(market);
        // ItemOption추가
        for(ItemOption itemOption : itemOptions) {
            item.addItemOption(itemOption);
        }
        // ItemThumbnailImage추가
        for(ItemThumbnailImage itemThumbnailImage : itemThumbnailImages) {
            item.addItemThumbnailImage(itemThumbnailImage);
        }
        // HashtagItem추가
        for(HashtagItem hashtagItem : hashtagItems) {
            item.addHashtagItem(hashtagItem);
        }

        return item;
    }

    //== 연관관계 편의 메서드 ==//
    public void setMarket(Market market) {
        this.market = market;
        market.addItem(this);
    }

    public void addItemOption(ItemOption itemOption) {
        itemOptions.add(itemOption);
        itemOption.setItem(this);
    }

    public void addItemThumbnailImage(ItemThumbnailImage itemThumbnailImage) {
        itemThumbnailImages.add(itemThumbnailImage);
        itemThumbnailImage.setItem(this);
    }

    public void addHashtagItem(HashtagItem hashtagItem) {
        hashtagItems.add(hashtagItem);
        hashtagItem.setItem(this);
    }
}
