package jandy3.DesignSeller.auth.service;

import java.util.List;
import java.util.Optional;

import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.provider.KakaoUserInfo;
import jandy3.DesignSeller.auth.provider.OAuth2UserInfo;
import jandy3.DesignSeller.domain.User;
import jandy3.DesignSeller.repository.UserRepository;
import jandy3.DesignSeller.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;

    private final UserService userService;

    // userRequest 는 code를 받아서 accessToken을 응답 받은 객체
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest); // kakao

        // code를 통해 구성한 정보
        System.out.println("userRequest clientRegistration : " + userRequest.getClientRegistration());
        System.out.println("userRequest token : " + userRequest.getAccessToken().getTokenValue());
        // token을 통해 응답받은 회원정보
        System.out.println("oAuth2User : " + oAuth2User);
//        return super.loadUser(userRequest);
        return processOAuth2User(userRequest, oAuth2User);
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {

        // Attribute를 파싱해서 공통 객체로 묶는다. 관리가 편함.
        OAuth2UserInfo oAuth2UserInfo = null;
        if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
            System.out.println("kakao login request");
            oAuth2UserInfo = new KakaoUserInfo(oAuth2User.getAttributes());
        } else {
            System.out.println("only kakao");
        }

        //System.out.println("oAuth2UserInfo.getProvider() : " + oAuth2UserInfo.getProvider());
        //System.out.println("oAuth2UserInfo.getProviderId() : " + oAuth2UserInfo.getProviderId());
        List<User> findUsers =
                userRepository.findByProviderAndProviderId(oAuth2UserInfo.getProvider(), oAuth2UserInfo.getProviderId());
        User user;
        if (!findUsers.isEmpty() && findUsers.size() == 1) {
            user = findUsers.get(0);
            // user가 존재하면 update 해주기
            userService.update(user.getId(), oAuth2UserInfo.getName(), oAuth2UserInfo.getImage());
        } else {
            // user의 패스워드가 null이기 때문에 OAuth 유저는 일반적인 로그인을 할 수 없음.
            user = User.builder()
                    .name(oAuth2UserInfo.getProvider() + "_" + oAuth2UserInfo.getProviderId())
                    .nickname(oAuth2UserInfo.getName())
                    .email(oAuth2UserInfo.getEmail())
                    .profileImage(oAuth2UserInfo.getImage())
                    .role("ROLE_USER")
                    .provider(oAuth2UserInfo.getProvider())
                    .providerId(oAuth2UserInfo.getProviderId())
                    .build();
            userService.join(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}