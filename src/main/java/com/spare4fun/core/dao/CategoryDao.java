package com.spare4fun.core.dao;


import com.spare4fun.core.entity.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * save a new category to category table
     * @param category
     * @return category that is saved to table
     * @throws
     *      DuplicateCategoryException
     *        - if category with same category name already exist
     */
    Category saveCategory(Category category);

    /**
     * delete the category with categoryId from category table
     * do nothing if category doesn't exist
     * @param categoryId
     */
    void deleteCategoryById(int categoryId);

    /**
     * delete the category with name from category table
     * do nothing if category doesn't exist
     * @param categoryName
     */
    void deleteCategoryByName(String categoryName);

    /**
     * get a category by categoryId
     * @param categoryId
     * @return null iff the category doesn't exist
     */
    Category getCategoryById(int categoryId);

    /**
     * get a category by category name
     * @param categoryName
     * @return null iff the category doesn't exist
     */
    Category getCategoryByName(String categoryName);

    /**
     * @return list of all categories in table
     */
    List<Category> getAllCategories();

    /**
     * update a category
     * @param category
     *     requires not null field: category.Id
     * @return category that is udpated
     */
    Category updateCategory(Category category);
}
