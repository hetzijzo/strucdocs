package org.strucdocs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.http.HttpHeaders
import org.strucdocs.security.OAuthHelper
import spock.lang.Shared
import spock.lang.Specification

abstract class RestOAuthAbstractSpecification extends Specification {

    @Autowired
    TestRestTemplate restTemplate

    @Autowired
    OAuthHelper oauthHelper

    @Shared
    HttpHeaders headers

    def setup() {
        def accessToken = oauthHelper.getAccessToken("tester")

        headers = new HttpHeaders()
        headers.add(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken.getValue())
    }

//     TestRestTemplate getRestTemplateWithHalMessageConverter() {
//        List<HttpMessageConverter<?>> existingConverters = restTemplate.getMessageConverters();
//        List<HttpMessageConverter<?>> newConverters = new ArrayList<>();
//        newConverters.add(halJacksonHttpMessageConverter);
//        newConverters.addAll(existingConverters);
//        restTemplate.setMessageConverters(newConverters);
//        return restTemplate;
//    }
}
