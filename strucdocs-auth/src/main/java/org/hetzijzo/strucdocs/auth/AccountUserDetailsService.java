package org.hetzijzo.strucdocs.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountUserDetailsService implements UserDetailsService {

    private final AccountRepository accountRepository;

    @Autowired
    public AccountUserDetailsService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.accountRepository.findByUsername(username)
            .map(account -> new User(
                account.getUsername(),
                account.getPassword(),
                account.isActive(),
                account.isActive(),
                account.isActive(),
                account.isActive(),
                AuthorityUtils.createAuthorityList("ROLE_ADMIN", "ROLE_USER"))
            ).orElseThrow(() ->
                new UsernameNotFoundException(String.format("User %s not found", username))
            );
    }
}
