package org.strucdocs.component.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.ParameterizedTypeReference
import org.springframework.hateoas.Resource
import org.springframework.http.HttpEntity
import org.springframework.http.ResponseEntity
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.RestOAuthAbstractSpecification
import org.strucdocs.component.user.UserRepository
import org.strucdocs.model.User

import javax.transaction.Transactional

import static org.springframework.http.HttpMethod.GET
import static org.springframework.http.HttpStatus.OK

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
class UserControllerSpecIT extends RestOAuthAbstractSpecification {

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    def "should get current user"() {
        given: "A default tester user"
        when: "The current user is requested"
            ResponseEntity<Resource<User>> response = restTemplate.exchange(
                '/user', GET,
                new HttpEntity<>(headers),
                new ParameterizedTypeReference<Resource<User>>() {})
        then: "A status 200 should be returned"
            response.statusCode == OK
        then: "The user should contain the correct information"
            User user = response.body.content
            with(user) {
                [name] == ['Tester']
                [username] == ['tester']
                [uuid] != null
            }
    }
}
