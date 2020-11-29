package com.spare4fun.core.dao;


import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

public interface UserDao {
    Optional<User> selectUserByUsername(String username);

    void addUser(User user) throws DuplicateUserException;

    void deleteUserByUsername(String username) throws UsernameNotFoundException;
}
