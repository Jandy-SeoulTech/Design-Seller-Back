package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.*;
import jandy3.DesignSeller.dto.CreateItemDto;
import jandy3.DesignSeller.dto.ItemOptionDto;
import jandy3.DesignSeller.repository.HashtagRepository;
import jandy3.DesignSeller.repository.ItemRepository;
import jandy3.DesignSeller.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final UserService userService;
    private final MarketService marketService;
    private final ItemRepository itemRepository;
    private final HashtagRepository hashtagRepository;

    /**
     * 상품 등록
     */
    @Transactional
    public void createItem(Item item) {
        itemRepository.save(item);
    }
}
