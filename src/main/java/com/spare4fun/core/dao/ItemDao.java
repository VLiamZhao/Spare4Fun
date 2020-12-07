package com.spare4fun.core.dao;

import com.spare4fun.core.entity.Item;

import java.util.List;

public interface ItemDao {
    /**
     * save a new item to item table
     * @param item
     * @return item that is saved to table
     */
    Item saveItem(Item item);

    /**
     * get an item from table with the itemId
     * @param itemId
     * @return null iff the item with itemId doesn't exist
     */
    Item getItemById(int itemId);

    /**
     * delete the item with itemId from the table
     * @param itemId
     */
    void deleteItemById(int itemId);

    /**
     * @return list of all items from item table
     */
    List<Item> getAllItems();
}