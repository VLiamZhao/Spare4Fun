package com.spare4fun.core.controller;

import com.spare4fun.core.dto.MessageDto;
import com.spare4fun.core.dto.UserDto;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.exception.DuplicateUserException;
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class RegistrationController {
    private Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeMap<UserDto, User> userMapper;

    @PostMapping("/user/register")
    @ResponseBody
    public MessageDto register(@RequestBody UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.map(userDto);
        try {
            userService.addUser(user);
        } catch (DuplicateUserException e) {
            return MessageDto
                    .builder()
                    .status(MessageDto.Status.FAILURE)
                    .message(e.getMessage())
                    .build();
        }
        return MessageDto
                .builder()
                .status(MessageDto.Status.SUCCESS)
                .message("Created user successfully!")
                .build();
    }
}
