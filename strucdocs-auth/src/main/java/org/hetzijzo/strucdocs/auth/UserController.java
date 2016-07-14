package org.hetzijzo.strucdocs.auth;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
public class UserController {

    @RequestMapping("/user")
    public Principal principal(Principal principal) {
        return principal;
    }
}
