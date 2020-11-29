package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;
import com.spare4fun.core.exception.DuplicateUserException;

public interface ItemDao {

    /**
     * save a new item to DB
     * @author Jin Zhang
     * @param item
     */
    void addItem(Item item);


}
