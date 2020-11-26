package com.spare4fun.core.controller;

import com.spare4fun.core.dto.ItemDto;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.service.SearchItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchItemService searchItemService;

    /**
     * Only search for exact match of name
     * @author Haitao
     * @version 1.0
     */
    // /search?keyword=name
    @GetMapping("{keyword}")
    @ResponseBody
    public List<ItemDto> searchByKeyword(@PathVariable("keyword") String keyword) {
        List<Item> items = searchItemService.searchByName(keyword);
        List<ItemDto> resItems = new ArrayList<>();
        for (Item item : items) {
        }
        return resItems;
    }
}
