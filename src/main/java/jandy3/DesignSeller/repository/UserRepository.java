package jandy3.DesignSeller.repository;

import jandy3.DesignSeller.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepository {

    private final EntityManager em;

    public void save(User user) {
        em.persist(user);
    }

    public User findOne(Long id) {
        return em.find(User.class, id);
    }

    public User findByUsername(String username) {
        return em.createQuery("select u from User as u where u.username = :username", User.class)
                .setParameter("username", username)
                .getSingleResult();
    }
    // SELECT * FROM user WHERE provider = ?1 and providerId = ?2

    public List<User> findByProviderAndProviderId(String provider, String providerId) {
        return em.createQuery("select u from User as u where u.provider = :provider and u.providerId = :providerId", User.class)
                .setParameter("provider", provider)
                .setParameter("providerId", providerId)
                .getResultList();
    }
}
