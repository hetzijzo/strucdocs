package org.strucdocs.security

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.security.oauth2.provider.token.TokenStore
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore

@Configuration
@Order(90)
class TestSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers('/v2/api-docs')
    }

    @Bean
    @Primary
    AuthorizationServerTokenServices testTokenServices() {
        final DefaultTokenServices defaultTokenServices = new DefaultTokenServices()
        defaultTokenServices.setAccessTokenValiditySeconds(-1)
        defaultTokenServices.setTokenStore(testTokenStore())
        return defaultTokenServices
    }

    @Bean
    TokenStore testTokenStore() {
        new InMemoryTokenStore()
    }
}
