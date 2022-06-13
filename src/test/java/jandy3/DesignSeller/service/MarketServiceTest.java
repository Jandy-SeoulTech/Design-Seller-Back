package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.Market;
import jandy3.DesignSeller.domain.User;
import jandy3.DesignSeller.repository.MarketRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class MarketServiceTest {
    @Autowired
    MarketService marketService;
    @Autowired
    EntityManager em;
    @Autowired
    MarketRepository marketRepository;

    @Test
    public void 마켓생성() {
        //given
        User user = createUser();

        //when
        Market market = new Market();
        market.setName("테스트 마켓");
        market.setDescription("테스트 마켓 설명");
        market.setUser(user);

        Long savedId = marketService.createMarket(market);

        //then
        assertEquals(market, marketRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class)
    public void 마켓생성_에러() throws Exception {
        //given
        User user = createUser();
        Market market1 = new Market();

        market1.setName("테스트 마켓1");
        market1.setDescription("테스트 마켓1 설명");
        market1.setUser(user);

        Market market2 = new Market();
        market2.setName("테스트 마켓2");
        market2.setDescription("테스트 마켓2 설명");
        market2.setUser(user);

        //when
        marketService.createMarket(market1);
        marketService.createMarket(market2);

        //then
        fail("마켓은 한 회원 당 하나만 생성할 수 있다.");
    }

    private User createUser() {
        User user = new User();
        em.persist(user);
        return user;
    }
}