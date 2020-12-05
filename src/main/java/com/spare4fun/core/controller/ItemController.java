package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import com.spare4fun.core.dto.LocationDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.User;
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
@RequestMapping("/item")
public class ItemController {
    @Autowired

    private UserService userService;

    @Autowired
    private TypeMap<Item, ItemDto> itemDtoMapper;

    @Autowired
    private TypeMap<Location, LocationDto> locationDtoMapper;

    @GetMapping("/seller/getAllItems")
    @ResponseBody
    public List<ItemDto> getAllItems() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<ItemDto> res = new ArrayList<>();
        Optional<User> user = userService.loadUserByUsername(username);
        if (user.isEmpty()) {
            // if user doesn't exists
            return res;
        }

        List<Item> items = user.get().getItems();
        items
                .stream()
                .forEach( item -> {
                    ItemDto itemDto = itemDtoMapper.map(item);
                    if (!item.isHideLocation()) {
                        itemDto.setLocationDto(locationDtoMapper.map(item.getLocation()));
                    }
                    res.add(itemDto);
                });
        return res;
    }
}
