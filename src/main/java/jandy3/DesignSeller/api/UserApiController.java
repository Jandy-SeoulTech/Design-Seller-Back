package jandy3.DesignSeller.api;

import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.oauth.annotation.CurrentUser;
import jandy3.DesignSeller.oauth.exception.ResourceNotFoundException;
import jandy3.DesignSeller.domain.User;
import jandy3.DesignSeller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final ObjectMapper mapper;

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String user(@CurrentUser PrincipalDetails principalDetails) throws JsonProcessingException {
        User userById =  userRepository.findById(principalDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", principalDetails.getId()));
        return mapper.writeValueAsString(userById);
    }

    @PostMapping(value = "/join")
    public @ResponseBody Long join(User user) {
        user.setRole("ROLE_USER");
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        System.out.println(user.getId());

        userRepository.save(user);

        return user.getId();
    }
}