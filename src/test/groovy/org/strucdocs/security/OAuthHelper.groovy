package org.strucdocs.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.authentication.TestingAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.AuthorityUtils
import org.springframework.security.oauth2.common.OAuth2AccessToken
import org.springframework.security.oauth2.provider.OAuth2Authentication
import org.springframework.security.oauth2.provider.OAuth2Request
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.stereotype.Component

@Component
class OAuthHelper {

    @Autowired
    AuthorizationServerTokenServices tokenservice

    OAuth2AccessToken getAccessToken(final String username, String... authorities) {
        OAuth2Request oauth2Request =
            new OAuth2Request(null, "strucdocs", AuthorityUtils.createAuthorityList(authorities), true,
                Collections.singleton("openid"), null, null, null, null)
        Authentication userauth = new TestingAuthenticationToken(username, null, authorities)
        OAuth2Authentication oauth2auth = new OAuth2Authentication(oauth2Request, userauth)
        tokenservice.createAccessToken(oauth2auth)
    }
}
