package org.strucdocs.component.security

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ContextConfiguration
import org.strucdocs.component.user.UserRepository
import org.strucdocs.model.User
import spock.lang.Specification

import javax.transaction.Transactional

@ContextConfiguration
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
@Rollback
class UserServiceSpecIT extends Specification {

    @Autowired
    UserService userService

    @Autowired
    UserRepository userRepository

    def "should save user and not recreate"() {
        given: "No User"
        when: "Saving a user with username and name"
            User user = userService.getUser('username1', 'User 1')
        then: "A new user is returned"
            with(user) {
                [name] == ['User 1']
                [username] == ['username1']
                [uuid] != null
            }
        then: "the user exists in the database"
            userRepository.findByUsername('username1') != null

        when: "Resaving the user"
            User user2 = userService.getUser('username1', 'User 1')
        then: "User has correct uuid"
            user.uuid == user2.uuid
    }
}
