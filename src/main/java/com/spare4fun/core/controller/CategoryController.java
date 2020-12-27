package com.spare4fun.core.controller;

import com.spare4fun.core.dto.CategoryDto;
import com.spare4fun.core.entity.Category;
import com.spare4fun.core.service.CategoryService;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TypeMap<Category, CategoryDto> categoryDtoMapper;

    @GetMapping("/getAllCategories")
    @ResponseBody
    public List<CategoryDto> getAllCategories() {
        List<CategoryDto> res = new ArrayList<>();
        List<Category> categories = categoryService.getAllCategories();
        categories
                .stream()
                .forEach( category -> {
                    CategoryDto categoryDto = categoryDtoMapper.map(category);
                    res.add(categoryDto);
                });
        return res;
    }
}
