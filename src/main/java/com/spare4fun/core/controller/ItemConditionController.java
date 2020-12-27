package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemConditionDto;
import com.spare4fun.core.entity.ItemCondition;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.ItemConditionService;
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/itemCondition")
public class ItemConditionController {

    @Autowired
    private UserService userService;

    @Autowired
    private ItemConditionService itemConditionService;

    @Autowired
    private TypeMap<ItemCondition, ItemConditionDto> itemConditionDtoMapper;

    @GetMapping("/getAllItemCondition")
    @ResponseBody
    public List<ItemConditionDto> getAllItemCondition() {
        List<ItemConditionDto> result = new ArrayList<>();

        List<ItemCondition> itemConditions = itemConditionService.getAllItemCondition();
        itemConditions
                .stream()
                .forEach( itemCondition -> {
                    ItemConditionDto itemConditionDto = itemConditionDtoMapper.map(itemCondition);
                    result.add(itemConditionDto);
                });
        return result;
    }
}
