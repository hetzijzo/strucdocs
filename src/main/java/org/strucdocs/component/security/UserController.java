package org.strucdocs.component.security;

import lombok.RequiredArgsConstructor;

import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.strucdocs.model.User;

import java.util.Map;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/user")
    public ResponseEntity<Resource<User>> getUser(OAuth2Authentication oAuth2Authentication) {
        final Map<String, String> details =
            (Map<String, String>) oAuth2Authentication.getUserAuthentication().getDetails();

        return ResponseEntity.ok(
            new Resource<>(userService.getUser(details.get("id"), details.get("name")))
        );
    }
}
