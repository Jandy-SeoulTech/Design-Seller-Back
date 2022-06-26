package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.Hashtag;
import jandy3.DesignSeller.repository.HashtagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.NoResultException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HashtagService {
    private final HashtagRepository hashtagRepository;

    @Transactional
    public Long createHashtag(Hashtag hashtag) {
        hashtagRepository.save(hashtag);
        return hashtag.getId();
    }

    public Hashtag findByName(String name) {
        return hashtagRepository.findByName(name);
    }

    @Transactional
    public Hashtag createOrGetHashtag(String name) {
        Hashtag hashtag;
        try {
            hashtag = hashtagRepository.findByName(name);
        } catch(NoResultException e) {
            hashtag = Hashtag.createHashtag(name);
            hashtagRepository.save(hashtag);
        }
        return hashtag;
    }
}
