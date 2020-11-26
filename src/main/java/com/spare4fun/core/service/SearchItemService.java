package com.spare4fun.core.service;

import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchItemService {
    @Autowired
    private ItemDao itemDao;

    public List<Item> searchByName(String itemName) {
        return itemDao.searchByName(itemName);
    }
}
