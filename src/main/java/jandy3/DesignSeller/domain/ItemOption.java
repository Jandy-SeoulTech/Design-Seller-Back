package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
public class ItemOption {
    @GeneratedValue
    @Id
    private Long id;

    private String name;

    private int price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @OneToMany(mappedBy = "itemOption")
    private List<ItemOptionValue> itemOptionDetails = new ArrayList<>();

    //== constructor ==//
    private ItemOption() {}
    private ItemOption(String name) {
        this.name = name;
    }

    //== 연관관계 편의 메서드 ==//
    public void setItem(Item item) {
        this.item = item;
    }

    public void addItemOptionDetails(ItemOptionValue itemOptionDetail) {
        itemOptionDetails.add(itemOptionDetail);
        itemOptionDetail.setItemOption(this);
    }
    //== 생성 메서드 ==//
    public static ItemOption createItemOption(String name, List<ItemOptionValue> itemOptionDetails) {
        ItemOption itemOption = new ItemOption(name);
        for(ItemOptionValue itemOptionDetail : itemOptionDetails) {
            itemOption.addItemOptionDetails(itemOptionDetail);
        }
        return itemOption;
    }


}
