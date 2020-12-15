package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.UserDao;
import com.spare4fun.core.entity.Role;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDao userDao;

    private List<User> users;

    @BeforeEach
    public void setup() {
        users = dummyUsers();
        users
                .stream()
                .forEach(user -> {
                    userDao.saveUser(user);
                });
    }

    @AfterEach
    public void clean() {
        users
                .stream()
                .forEach(user -> {
                    userDao.deleteUserById(user.getId());
                });
    }



    private List<User> dummyUsers() {
        List<User> dummyUsers = new ArrayList<>();

        User alice = User
                .builder()
                .email("dummy1")
                .password(passwordEncoder.encode("pass"))
                .role(Role.ADMIN)
                .enabled(true)
                .build();
        dummyUsers.add(alice);

        User jin = User
                .builder()
                .email("dummy2")
                .password(passwordEncoder.encode("pass"))
                .role(Role.USER)
                .enabled(true)
                .build();
        dummyUsers.add(jin);
        return dummyUsers;
    }

    @Test
    public void testAddUser() {
        // cannot add duplicate user with same username
        users
                .stream()
                .forEach(
                        user -> {
                            assertThrows(DuplicateUserException.class, () -> {
                                userDao.saveUser(user);
                            });
                        }
                );
    }

    @Test
    public void testDeleteUser() {
        // cannot add duplicate user with same username
        User yuhe = User
                .builder()
                .email("dummy3")
                .password(passwordEncoder.encode("pass"))
                .role(Role.USER)
                .enabled(true)
                .build();
        userDao.saveUser(yuhe);
        assertThat(userDao.getUserById(yuhe.getId())).isNotNull();
        userDao.deleteUserById(yuhe.getId());
        assertThat(userDao.getUserById(yuhe.getId())).isNull();
    }

    @Test
    public void testGetAll() {
        List<User> usersFromTable = userDao.getAllUsers();
        Collections.sort(usersFromTable, Comparator.comparing(User::getUsername));

        IntStream.range(0, usersFromTable.size())
                .forEach(idx -> {
                    assertThat(usersFromTable.get(idx).getUsername())
                            .isEqualTo(users.get(idx).getUsername());
                });
    }

    @Test
    public void contextLoad() {
        assertThat(userDao).isNotNull();
    }
}
