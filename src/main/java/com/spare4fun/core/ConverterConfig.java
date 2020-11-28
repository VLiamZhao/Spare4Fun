package com.spare4fun.core;

import com.spare4fun.core.dto.UserDto;
import com.spare4fun.core.entity.User;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ConverterConfig {
    @Autowired
    private ModelMapper modelMapper;

    @Bean
    public ModelMapper getModelMapper() {
        return new ModelMapper();
    }

    @Bean
    public TypeMap<UserDto, User> convertUserDtoToUser() {
        return modelMapper.typeMap(UserDto.class, User.class).addMappings(mapper -> {
            mapper.map(src -> src.getUsername(), User::setEmail);
            mapper.map(src -> src.getPassword(), User::setPassword);
        });
    }
}
