package com.spare4fun.core.dao;


import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
    /**
     * select User with username from user table
     * used by Spring Security
     * @param username
     * @return Optional.empty() if user with username / email doesn't exist
     *  otherwise return Optional of the user
     */
    Optional<User> selectUserByUsername(String username);

    /**
     * save a new user to user table
     * @param user
     * @throws
     *  DuplicateUserException - iff user with same username / email already exist
     */
    User saveUser(User user);

    /**
     * delete user from the user table
     * do nothing if user is not found
     * @param userId
     */
    void deleteUserById(int userId);

    /**
     * select User with userId from user table
     * @param userId
     * @return null iff the user with userId doesn't exists
     */
    User getUserById(int userId);

    /**
     * @return list of all users from user table
     */
    List<User> getAllUsers();
}
