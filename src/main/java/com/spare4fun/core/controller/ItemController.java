package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

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


