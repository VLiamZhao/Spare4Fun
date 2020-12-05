package com.spare4fun.core.service;

import com.google.common.annotations.VisibleForTesting;
import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Role;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * User Service for backend
 * @author Xinrong Zhao
 * @version 1.0
 */
@Service
public class UserService {
    @Autowired
    private UserDao userDao;

    public void saveUser(User user) throws DuplicateUserException {
        user.setRole(Role.USER);
        user.setEnabled(true);
        userDao.saveUser(user);
    }

    public Optional<User> loadUserByUsername(String username) {
        return userDao.selectUserByUsername(username);
    }

    public void deleteUserById(int userId){
        userDao.deleteUserById(userId);
    }
}
