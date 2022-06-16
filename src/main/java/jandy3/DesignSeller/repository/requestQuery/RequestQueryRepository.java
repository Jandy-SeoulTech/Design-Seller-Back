package jandy3.DesignSeller.repository.requestQuery;

import jandy3.DesignSeller.dto.RequestFlatDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class RequestQueryRepository {
    private final EntityManager em;

    public List<RequestFlatDto> findByDto_flat() {
        String sql = "select new jandy3.DesignSeller.dto.RequestFlatDto" +
                "(r.id, r.createDate, p.thumbnailImage, c.name, p.id, p.name, po.id, po.name, po.price, pr.count, r.totalPrice, r.status)" +
                " from Request r" +
                " join r.productionRequests pr" +
                " join pr.productionOption po" +
                " join po.production p" +
                " join p.company c";

        return em.createQuery(sql, RequestFlatDto.class)
                .getResultList();
    }
}
