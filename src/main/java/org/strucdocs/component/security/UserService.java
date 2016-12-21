package org.strucdocs.component.security;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.strucdocs.component.user.UserRepository;
import org.strucdocs.model.User;

import java.util.Optional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
class UserService {

    private final UserRepository userRepository;

    User getUser(String username, String name) {
        //byte[] picture = oAuth2RestTemplate.getForObject("https://graph.facebook.com/me/picture", byte[].class);
        return Optional.ofNullable(userRepository.findByUsername(username))
            .orElse(userRepository.save(User.builder().username(username).name(name).build()));
    }
}
