package com.spare4fun.core.test.dao;

import com.spare4fun.core.dao.CategoryDao;
import com.spare4fun.core.entity.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Profile;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Profile("test")
public class CategoryDaoTest {
    @Autowired
    private CategoryDao categoryDao;

    private List<Category> categories;

//    @AfterEach
//    public void clean() {
//        categories.forEach(category -> {
//            categoryDao.deleteCategoryById(category.getId());
//        });
//    }
//
//    public List<Category> dummyCategories() {
//        List<Category> res = new ArrayList<>();
//
//        res.add(
//                Category
//                        .builder()
//                        .category("book")
//                        .build()
//        );
//
//        res.add(
//                Category
//                        .builder()
//                        .category("furniture")
//                        .build()
//        );
//
//        return res;
//    }

    @BeforeEach
    public void setUp() {
        categories = categoryDao.getAllCategories();
    }

    @Test
    public void testSaveCategory() {
        categories.forEach(category -> {
            assertThat(categoryDao.getCategoryById(category.getId())).isNotNull();
        });
    }

    @Test
    public void testDeleteCategoryByName() {
        Category c = Category
                .builder()
                .category("Accessory")
                .build();
        categoryDao.saveCategory(c);
        assertThat(categoryDao.getCategoryById(c.getId())).isNotNull();
        categoryDao.deleteCategoryByName(c.getCategory());
        assertThat(categoryDao.getCategoryById(c.getId())).isNull();
    }

    @Test
    public void testDeleteCategoryById() {
        Category c = Category
                .builder()
                .category("Accessory")
                .build();
        categoryDao.saveCategory(c);
        assertThat(categoryDao.getCategoryById(c.getId())).isNotNull();
        categoryDao.deleteCategoryById(c.getId());
        assertThat(categoryDao.getCategoryById(c.getId())).isNull();
    }

    @Test
    public void testGetCategoryById() {
        Category c = categoryDao.getCategoryById(categories.get(0).getId());
        assertThat(c.getId()).isEqualTo(categories.get(0).getId());
        assertThat(c.getCategory()).isEqualTo(categories.get(0).getCategory());
    }

    @Test
    public void testGetCategoryByName() {
        Category c = categoryDao.getCategoryByName(categories.get(0).getCategory());
        assertThat(c.getId()).isEqualTo(categories.get(0).getId());
        assertThat(c.getCategory()).isEqualTo(categories.get(0).getCategory());
    }

    @Test
    public void testGetAllCategories() {
        List<Category> cs = categoryDao.getAllCategories();
        assertThat(cs.size()).isEqualTo(categories.size());
    }

//    @Test
//    public void testUpdateCategory() {
//        Category category = categoryDao.getCategoryById(categories.get(0).getId());
//        assertThat(category.getCategory()).isEqualTo("book");
//        category.setCategory("textbook");
//        categoryDao.updateCategory(category);
//        category = categoryDao.getCategoryById(categories.get(0).getId());
//        assertThat(category.getCategory()).isEqualTo("textbook");
//    }
}
