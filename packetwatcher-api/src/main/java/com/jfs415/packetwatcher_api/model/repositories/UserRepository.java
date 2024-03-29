package com.jfs415.packetwatcher_api.model.repositories;

import com.jfs415.packetwatcher_api.model.user.Authority;
import com.jfs415.packetwatcher_api.model.user.User;
import com.jfs415.packetwatcher_api.model.user.UserActivationState;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /**
     * Equivalent to SQL query "SELECT * from Users WHERE email = other_email".
     * These SpringBoot queries are escaped and therefore safe from SQL Injection.
     *
     * @param email The users email address.
     * @return A user with the corresponding email
     */
    Optional<User> findByEmail(String email);

    /**
     * Equivalent to SQL query "SELECT * from Users WHERE username = other_username".
     * These SpringBoot queries are escaped and therefore safe from SQL Injection.
     *
     * @param username The user's username.
     * @return A user with the corresponding username.
     */
    Optional<User> findByUsername(String username);

    List<User> findAllByLevelIsLessThanEqual(Authority authority);

    List<User> findAllByUserActivationState(UserActivationState activationState);

    /**
     * Check if an existing user already has this email.
     *
     * @param email The email to check for.
     * @return True if an existing user has this email address, otherwise false.
     */
    boolean existsByEmail(String email);

    /**
     * Check if an existing user already has this username.
     *
     * @param username The username to check for.
     * @return True if an existing user has this username, otherwise false.
     */
    boolean existsByUsername(String username);
}
