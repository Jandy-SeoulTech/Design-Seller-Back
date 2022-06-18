package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import jandy3.DesignSeller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"유저 API"})
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
}
