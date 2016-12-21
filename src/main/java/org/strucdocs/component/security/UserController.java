package org.strucdocs.component.security;

import lombok.RequiredArgsConstructor;

import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.strucdocs.component.user.UserRepository;
import org.strucdocs.model.User;

import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final OAuth2RestTemplate oAuth2RestTemplate;

    @RequestMapping("/user")
    public User user(OAuth2Authentication oAuth2Authentication) {
        final Map<String, String> details =
            (Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails();

        String name = details.get("name");
        String id = details.get("id");

        //byte[] picture = oAuth2RestTemplate.getForObject("https://graph.facebook.com/me/picture", byte[].class);

        return Optional.ofNullable(userRepository.findByUsername(id))
            .orElse(userRepository.save(User.builder().username(id).name(name).build()));
    }
}
