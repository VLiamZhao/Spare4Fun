package com.spare4fun.core.test.controller;

import com.spare4fun.core.controller.RegistrationController;
import com.spare4fun.core.dto.MessageDto;
import com.spare4fun.core.dto.UserDto;
import com.spare4fun.core.entity.Role;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import com.spare4fun.core.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class RegistrationControllerTest {
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RegistrationController registerationController;

    @Autowired
    private UserService userService;

    @BeforeEach
    private void setup() {
        // cannot add duplicate user with same username
        dummyUsers()
                .stream()
                .forEach(userDto -> {
                    MessageDto messageDto = registerationController.register(userDto);
                    assertThat(messageDto).isNotNull();
                    assertThat(messageDto.getStatus()).isEqualTo(MessageDto.Status.SUCCESS);
                });
    }

    @AfterEach
    private void clean() {
        dummyUsers()
                .stream()
                .forEach( userDto -> {
                    userService.deleteUser(userDto.getUsername());
                });
    }

    private Set<UserDto> dummyUsers() {
        Set<UserDto> dummyUsers = new HashSet<>();

        UserDto alice = UserDto
                .builder()
                .username("dummy1")
                .password("pass")
                .build();
        dummyUsers.add(alice);

        UserDto jin = UserDto
                .builder()
                .username("dummy2")
                .password("pass")
                .build();
        dummyUsers.add(jin);
        return dummyUsers;
    }

    @Test
    public void contextLoad() {
        assertThat(registerationController).isNotNull();
        assertThat(passwordEncoder).isNotNull();
        assertThat(userService).isNotNull();
    }

    @Test
    public void correctInfo() {
        dummyUsers()
                .stream()
                .forEach( userDto -> {
                    User user = userService.loadUserByUsername(userDto.getUsername()).orElse(null);
                    assertThat(user).isNotNull();
                    assertTrue(passwordEncoder.matches("pass", user.getPassword()));
                    assertThat(user.getRole()).isEqualTo(Role.USER);
                    assertThat(user.isEnabled()).isEqualTo(true);
                });
    }
}
