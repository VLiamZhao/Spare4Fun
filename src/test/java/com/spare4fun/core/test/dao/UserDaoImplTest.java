package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.UserDaoImpl;
import com.spare4fun.core.entity.Role;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserDaoImplTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserDaoImpl userDaoImpl;

    @BeforeEach
    public void setup() {
        dummyUsers()
                .stream()
                .forEach(user -> {
                    try {
                        userDaoImpl.addUser(user);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @AfterEach
    public void clean() {
        dummyUsers()
                .stream()
                .forEach(user -> {
                    try {
                        userDaoImpl.deleteUserByUsername(user.getUsername());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    private Set<User> dummyUsers() {
        Set<User> dummyUsers = new HashSet<>();

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
        dummyUsers()
                .stream()
                .forEach(
                        user -> {
                            assertThrows(DuplicateUserException.class, () -> {
                                userDaoImpl.addUser(user);
                            });
                        }
                );
    }

    @Test
    public void testDeleteUser() {
        // cannot add duplicate user with same username
        assertThrows(UsernameNotFoundException.class, () -> {
            userDaoImpl.deleteUserByUsername("dummy0");
        });
    }

    @Test
    public void contextLoad() {
        assertThat(userDaoImpl).isNotNull();
    }
}
