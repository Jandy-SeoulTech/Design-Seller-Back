package jandy3.DesignSeller.controller;

import jandy3.DesignSeller.model.User;
import jandy3.DesignSeller.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpHeaders;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    @GetMapping(value = "/user")
    public String user() {
        return "user";
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

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String kakaoCallBack(String code) {

//        RestTemplate rt = new RestTemplate();
//        HttpHeaders headers = new HttpHeaders();
//        headers.add("Contents-type", "application/x-www-form-urlencoded;charset=utf-8");
//
//        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
//        params.add("grant_type", "authorization_code");
//        params.add("client_id", "f48783f49d03bef07c4c21b53e293db9");
//        params.add("client_secret", "g7mjZblRpHqyt9nhsFL6PoHu4kOWhwvm");
//        params.add("redirect_uri","http://localhost:8080/login/oauth2/code/kakao");
//        params.add("code", code);
//
//        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);
//
//        ResponseEntity response = rt.exchange(
//                "https://kauth.kakao.com/oauth/token",
//                HttpMethod.POST,
//                kakaoTokenRequest,
//                String.class
//        );
//        System.out.println("response: " + response);
//        return "response: " + response;
        return "redirect:/auth?code="+"12341234";
    }
}
