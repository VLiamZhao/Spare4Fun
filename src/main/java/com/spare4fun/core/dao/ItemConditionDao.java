package com.spare4fun.core.dao;

import com.spare4fun.core.entity.ItemCondition;

import java.util.List;

public interface ItemConditionDao {
    /**
     * save a new itemCondition to itemCondition table
     * @param itemCondition
     * @return itemCondition that is saved to table
     * @throws
     *      DuplicateItemConditionException
     *        - if itemCondition with same itemCondition name already exist
     */
    ItemCondition saveItemCondition(ItemCondition itemCondition);

    /**
     * delete the itemCondition with itemConditionId from itemCondition table
     * do nothing if itemCondition doesn't exist
     * @param itemConditionId
     */
    void deleteItemConditionById(int itemConditionId);

    /**
     * delete the itemCondition with name from itemCondition table
     * do nothing if itemCondition doesn't exist
     * @param itemConditionName
     */
    void deleteItemConditionByName(String itemConditionName);

    /**
     * get a itemCondition by itemConditionId
     * @param itemConditionId
     * @return null iff the itemCondition doesn't exist
     */
    ItemCondition getItemConditionById(int itemConditionId);

    /**
     * get a itemCondition by itemCondition name
     * @param itemConditionName
     * @return null iff the itemCondition doesn't exist
     */
    ItemCondition getItemConditionByName(String itemConditionName);

    /**
     * @return list of all itemConditions in table
     */
    List<ItemCondition> getAllItemConditions();

    /**
     * update a itemCondition
     * @param itemCondition
     *     requires not null field: itemCondition.Id
     * @return itemCondition that is udpated
     */
    ItemCondition updateItemCondition(ItemCondition itemCondition);
}
