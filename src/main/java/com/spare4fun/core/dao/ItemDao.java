package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;

public interface ItemDao {
    //Please ignore this method. This is only for temporary test
    Item saveItem(Item item);

    void deleteItem(int itemId);
}