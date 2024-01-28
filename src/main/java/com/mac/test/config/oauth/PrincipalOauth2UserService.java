package com.mac.test.config.oauth;

import com.mac.test.config.auth.PrincipalDetails;
import com.mac.test.config.oauth.provider.GoogleUserInfo;
import com.mac.test.config.oauth.provider.NaverUserInfo;
import com.mac.test.config.oauth.provider.OAuth2UserInfo;
import com.mac.test.entity.User;
import com.mac.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
import java.util.Map;

import static com.fasterxml.jackson.databind.type.LogicalType.Map;

@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("userRequest = " + userRequest.getAccessToken());
        System.out.println("userRequest = " + userRequest.getClientRegistration());
        System.out.println("userRequest = " + super.loadUser(userRequest).getAttributes());

        OAuth2User oAuth2User = super.loadUser(userRequest);

        OAuth2UserInfo oAuth2UserInfo = null;

        if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
            System.out.println("Google 로그인 요청");
            oAuth2UserInfo = new GoogleUserInfo(oAuth2User.getAttributes());
        }
        else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
            System.out.println("Naver 로그인 요청");
            oAuth2UserInfo = new NaverUserInfo((Map<String, Object>) oAuth2User.getAttributes().get("response"));
        }
        else {
            System.out.println("우리는 구글,네이버 로그인만 지원합니다.");
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String provider = oAuth2UserInfo.getProvider();
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId;
        String password = encoder.encode("tony");
        String role = "ROLE_USER";

        User user = userRepository.findByUsername(username);
        if (ObjectUtils.isEmpty(user)) {
            System.out.println("oAuth 최초 로그인");
            user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setProvider(provider);
            user.setProviderId(providerId);
            user.setRole(role);
            user.setCreateDate(LocalDateTime.now());
            userRepository.save(user);
        }

        return new PrincipalDetails(user, oAuth2User.getAttributes());
    }
}
