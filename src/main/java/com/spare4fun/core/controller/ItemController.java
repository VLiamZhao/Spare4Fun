package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class ItemController {

    @PostMapping("/item/creation")
    @ResponseBody
    public ItemDto createItem(@RequestBody ItemDto itemDto) {

    }
}
