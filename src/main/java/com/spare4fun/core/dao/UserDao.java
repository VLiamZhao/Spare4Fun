package com.spare4fun.core.dao;

import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserDao {
    /**
     * Get the user by username
     * @param username
     * @return user optional
     */
    Optional<User> selectUserByUsername(String username);

    /**
     * save a new user to DB
     * @author Xinrong Zhao
     * @param user
     * @throws
     *    DuplicateUserException - if duplicate user is found
     */
    void addUser(User user) throws Exception;

    /**
     * delete a user from DB
     * @author Xinrong Zhao
     * @param user
     * @throws
     *    UsernameNotFoundException - if no user is found
     */
    void deleteUserByUsername(String username) throws Exception;
}
