package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;
import com.spare4fun.core.exception.ItemNotFoundException;

public interface ItemDao {

    /**
     * save a new item to DB
     * @author Jin Zhang
     * @param item
     */

    Item createItem(Item item);

    // deleteItem , boolean, throw exception
    boolean deleteItem(Item item) throws ItemNotFoundException;

    Item getItemById(int itemId);
}
