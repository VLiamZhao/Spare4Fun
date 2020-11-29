package com.spare4fun.core.service;

import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.entity.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    ItemDao itemDao;

    //Please ignore this method. This is only for temporary test
    public Item saveItem(Item item){
        return itemDao.saveItem(item);
    }
}