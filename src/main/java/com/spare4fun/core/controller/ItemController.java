package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.spare4fun.core.dto.LocationDto;
import com.spare4fun.core.entity.Location;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.ItemService;
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/item")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private ItemService itemService;

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

    @PostMapping("/creation")
    @ResponseBody
    public ItemDto createItem(@RequestBody ItemDto itemDto) {
        // step1: map itemDto -> item
        // Item item = Item.builder().title(itemDto.getTitle()).build;
        // step2: call service to save item
        // itemService.saveItem(item)
        // step3: map item -> itemDto & return
        return null;
        //return new ResponseEntity<String>("The item has been successfully placed!", HttpStatus.OK);
    }
}
