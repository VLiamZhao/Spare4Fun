package com.spare4fun.core.service;

import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.entity.Item;
import com.spare4fun.core.exception.InvalidPriceException;
import com.spare4fun.core.exception.InvalidQuantityException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
    @Autowired
    ItemDao itemDao;

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
     * @return item that is saved
     * @throws
     *    InvalidPriceException
     *       - if price <= 0
     *    InvalidQuantityException
     *       - if quantity <= 0
     */
    public Item saveItem(Item item) {
        if (item.getListingPrice() <= 0) {
            throw new InvalidPriceException("Price is invalid");
        }
        if (item.getQuantity() <= 0) {
            throw new InvalidQuantityException("Quantity is invalid");
        }
        return itemDao.saveItem(item);
    }

    public Item getItemById(int itemId) {
        return itemDao.getItemById(itemId);
    }

    public void deleteItemById(int itemId) {
        itemDao.deleteItemById(itemId);
    }

    public Item updateItem(Item item){
        return itemDao.updateItem(item);
    }
}