package com.spare4fun.core.controller;

import com.spare4fun.core.dto.CategoryDto;
import com.spare4fun.core.entity.Category;
import com.spare4fun.core.entity.User;
import com.spare4fun.core.service.CategoryService;
import com.spare4fun.core.service.UserService;
import org.modelmapper.TypeMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private TypeMap<Category, CategoryDto> categoryDtoMapper;

    @GetMapping("/getAllCategories")
    @ResponseBody
    public List<CategoryDto> getAllCategories() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        List<CategoryDto> res = new ArrayList<>();
        Optional<User> user = userService.loadUserByUsername(username);
        if (user.isEmpty()) {
            // if user doesn't exists
            return res;
        }
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
