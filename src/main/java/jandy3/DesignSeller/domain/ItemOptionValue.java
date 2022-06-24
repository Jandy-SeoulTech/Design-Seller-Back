package jandy3.DesignSeller.domain;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Getter
public class ItemOptionValue {
    @GeneratedValue
    @Id
    @Column(name = "item_option_detail_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "item_option_id")
    private ItemOption itemOption;

    private String name;
    private int stockQuantity;
    private Boolean isRequire;

    //== 연관관계 편의 메서드 ==//
    public void setItemOption(ItemOption itemOption) {
        this.itemOption = itemOption;
    }

    //== 생성 메서드 ==//
    public static ItemOptionValue createItemOptionValue(String name, int stockQuantity, boolean isRequire) {
        return new ItemOptionValue(name, stockQuantity, isRequire);
    }

    //== constructor ==//
    private ItemOptionValue() {}

    private ItemOptionValue(String name, int stockQuantity, boolean isRequire) {
        this.name = name;
        this.stockQuantity = stockQuantity;
        this.isRequire = isRequire;
    }
}
