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

    /**
     * TODO 1: check hideLocation default to false
     * TODO 2: check fixedPrice default to false
     * TODO 3: price > 0
     * TODO 4: quantity > 0
     * TODO 5: set quantityOnHold = 0
     * TODO 6: set quantitySold = 0
     * TODO 7: set availabilityTime default = "TBD"
     *
     * @param item
     * @return
     */
    public Item saveItem(Item item){
        return itemDao.saveItem(item);
    }

    public Item getItemById(int itemId) {
        return itemDao.getItemById(itemId);
    }

    public void deleteItemById(int itemId) {
        itemDao.deleteItemById(itemId);
    }
}