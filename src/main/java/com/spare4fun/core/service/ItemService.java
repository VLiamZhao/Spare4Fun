package com.spare4fun.core.service;

import com.spare4fun.core.dao.ItemDao;
import com.spare4fun.core.dao.ItemDaoImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Item Service
 * @author Jin Zhang
 * @version 1.0
 */
@Service
public class ItemService {
    @Autowired
    private ItemDaoImpl itemDaoImpl;}
