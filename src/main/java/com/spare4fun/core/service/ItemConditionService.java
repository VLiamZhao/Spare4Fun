package com.spare4fun.core.service;

import com.spare4fun.core.dao.ItemConditionDao;
import com.spare4fun.core.entity.ItemCondition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemConditionService {
    @Autowired
    private ItemConditionDao itemConditionDao;

    public List<ItemCondition> getAllItemCondition() {
        return itemConditionDao.getAllItemConditions();
    }
}
