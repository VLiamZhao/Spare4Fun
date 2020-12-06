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
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
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
    private ItemService itemService;

    @Autowired
    private UserService userService;

    @Autowired
    private TypeMap<Item, ItemDto> itemDtoMapper;

    @Autowired
    private TypeMap<Location, LocationDto> locationDtoMapper;


    @PostMapping("/creation")
    public ResponseEntity<String> saveItem(@RequestBody ItemDto ItemDto) {

//        Item item = new Item();

        return new ResponseEntity<String>("The item has been successfully placed!", HttpStatus.OK);
    }

    @GetMapping("/{itemId}")
    @ResponseBody
    public ItemDto getItemById(@PathVariable(value = "itemId") int itemId) {

        Item item = itemService.getItemById(itemId);
        ItemDto itemDto = ItemDto
                .builder()
                .sellerId(item.getSeller().getId())
                .locationId(item.getLocation().getId())
                .build();
        return itemDto;
    }

    @PostMapping("/deleteItem/{itemId}")
    public ResponseEntity<String> deleteItemById(@PathVariable(value = "itemId") int itemId) {
        itemService.deleteItemById(itemId);
        return new ResponseEntity<String>("The item has been successfully deleted!", HttpStatus.OK);
    }


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
