package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.ItemConditionDao;
import com.spare4fun.core.entity.ItemCondition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ItemConditionDaoTest {
    @Autowired
    private ItemConditionDao itemConditionDao;

    private List<ItemCondition> itemConditions;

    @BeforeEach
    public void setup() {
        itemConditions = dummyItemConditions();

        itemConditions.forEach( itemCondition -> {
            itemConditionDao.saveItemCondition(itemCondition);
        });
    }

    @AfterEach
    public void clean() {
        itemConditions.forEach(itemCondition -> {
            itemConditionDao.deleteItemConditionById(itemCondition.getId());
        });
    }

    public List<ItemCondition> dummyItemConditions() {
        List<ItemCondition> res = new ArrayList<>();

        res.add(
                ItemCondition
                        .builder()
                        .label("new")
                        .build()
        );

        res.add(
                ItemCondition
                        .builder()
                        .label("used-new")
                        .build()
        );

        return res;
    }

    @Test
    public void testSaveItemCondition() {
        itemConditions.forEach(itemCondition -> {
            assertThat(itemConditionDao.getItemConditionById(itemCondition.getId())).isNotNull();
        });
    }

    @Test
    public void testDeleteItemConditionByName() {
        ItemCondition c = ItemCondition
                .builder()
                .label("used-good")
                .build();
        itemConditionDao.saveItemCondition(c);
        assertThat(itemConditionDao.getItemConditionById(c.getId())).isNotNull();
        itemConditionDao.deleteItemConditionByName(c.getLabel());
        assertThat(itemConditionDao.getItemConditionById(c.getId())).isNull();
    }

    @Test
    public void testDeleteItemConditionById() {
        ItemCondition c = ItemCondition
                .builder()
                .label("used-fair")
                .build();
        itemConditionDao.saveItemCondition(c);
        assertThat(itemConditionDao.getItemConditionById(c.getId())).isNotNull();
        itemConditionDao.deleteItemConditionById(c.getId());
        assertThat(itemConditionDao.getItemConditionById(c.getId())).isNull();
    }

    @Test
    public void testGetItemConditionById() {
        ItemCondition c = itemConditionDao.getItemConditionById(itemConditions.get(0).getId());
        assertThat(c.getId()).isEqualTo(itemConditions.get(0).getId());
        assertThat(c.getLabel()).isEqualTo(itemConditions.get(0).getLabel());
    }

    @Test
    public void testGetItemConditionByName() {
        ItemCondition c = itemConditionDao.getItemConditionByName(itemConditions.get(0).getLabel());
        assertThat(c.getId()).isEqualTo(itemConditions.get(0).getId());
        assertThat(c.getLabel()).isEqualTo(itemConditions.get(0).getLabel());
    }

    @Test
    public void testGetAllItemConditions() {
        List<ItemCondition> cs = itemConditionDao.getAllItemConditions();
        assertThat(cs.size()).isEqualTo(itemConditions.size());
    }

    @Test
    public void testUpdateItemCondition() {
        ItemCondition itemCondition = itemConditionDao.getItemConditionById(itemConditions.get(0).getId());
        assertThat(itemCondition.getLabel()).isEqualTo("new");
        itemCondition.setLabel("very new");
        itemConditionDao.updateItemCondition(itemCondition);
        itemCondition = itemConditionDao.getItemConditionById(itemConditions.get(0).getId());
        assertThat(itemCondition.getLabel()).isEqualTo("very new");
    }
}
