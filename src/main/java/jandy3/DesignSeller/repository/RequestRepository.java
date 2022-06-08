package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.Request;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RequestRepository extends JpaRepository<Request, Long> {

}