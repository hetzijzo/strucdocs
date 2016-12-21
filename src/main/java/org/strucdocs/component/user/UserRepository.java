package org.strucdocs.component.user;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.strucdocs.model.User;

import java.util.UUID;

@RepositoryRestResource(exported = false)
public interface UserRepository extends PagingAndSortingRepository<User, UUID> {

    /**
     * Find a User by its username.
     *
     * @param username The username of the user
     * @return The User
     */
    User findByUsername(String username);
}
