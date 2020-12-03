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
    public ResponseEntity<String> saveItem(@RequestBody ItemDto ItemDto) {

//        Item item = new Item();

        return new ResponseEntity<String>("The item has been successfully placed!", HttpStatus.OK);
    }

    @GetMapping("/getItemById/{itemId}")
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
    public ResponseEntity<String> deleteItemById(@PathVariable(value = "itemId") int itemId){
        itemService.deleteItemById(itemId);
        return new ResponseEntity<String>("The item has been successfully deleted!", HttpStatus.OK);
    }
}
