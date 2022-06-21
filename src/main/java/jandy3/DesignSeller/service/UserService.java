package jandy3.DesignSeller.service;

import jandy3.DesignSeller.domain.User;
import jandy3.DesignSeller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;

    public User findOne(Long id) {
        return userRepository.findOne(id);
    }

    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    @Transactional
    public void update(Long id, String nickname, String profileImage) {
        User user = userRepository.findOne(id);
        user.setNickname(nickname);
        user.setProfileImage(profileImage);
    }
}
