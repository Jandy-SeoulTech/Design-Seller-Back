package jandy3.DesignSeller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.media.Schema;
import jandy3.DesignSeller.auth.PrincipalDetails;
import jandy3.DesignSeller.auth.annotation.CurrentUser;
import jandy3.DesignSeller.domain.User;
import jandy3.DesignSeller.repository.UserRepository;
import jandy3.DesignSeller.service.UserService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;


@Api(tags = {"유저 API"})
@RestController
@RequiredArgsConstructor
public class UserApiController {

    private final UserService userService;
    private final UserRepository userRepository;

    @ApiOperation(value = "유저 정보 조회")
    public UserDto getUserInfo(
            @ApiParam(value = "Authorization: Bearer {jwt}")
            @CurrentUser PrincipalDetails principalDetails
            ) {
        User user = userService.findOne(principalDetails.getId());

        return new UserDto(user.getNickname(), user.getEmail(), user.getProfileImage());
    }

    @Schema
    @Data
    @AllArgsConstructor
    static class UserDto {
        private String nickname;
        private String email;
        private String profileImage;
    }
}
