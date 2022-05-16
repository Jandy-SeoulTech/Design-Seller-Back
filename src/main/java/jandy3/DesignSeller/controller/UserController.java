package jandy3.DesignSeller.controller;

import jandy3.DesignSeller.config.auth.PrincipalDetails;
import jandy3.DesignSeller.config.oauth.annotation.CurrentUser;
import jandy3.DesignSeller.config.oauth.exception.ResourceNotFoundException;
import jandy3.DesignSeller.domain.User;
import jandy3.DesignSeller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping(value = "/user")
    @PreAuthorize("hasRole('ROLE_USER')")
    public String user(@CurrentUser PrincipalDetails principalDetails) {
        return String.valueOf(userRepository.findById(principalDetails.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", principalDetails.getId())));
    }

    @PostMapping(value = "/join")
    public @ResponseBody int join(User user) {
        user.setRole("ROLE_USER");
        String encPassword = bCryptPasswordEncoder.encode(user.getPassword());
        user.setPassword(encPassword);
        System.out.println(user.getId());

        userRepository.save(user);

        return user.getId();
    }
}
