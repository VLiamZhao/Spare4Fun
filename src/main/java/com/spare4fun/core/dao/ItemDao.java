package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;

public interface ItemDao {
    //Please ignore this method. This is only for temporary test
    Item saveItem(Item item);

    Item deleteItem(int itemId);

    Item getItemById(int itemId);
}