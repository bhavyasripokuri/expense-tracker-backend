package com.expense.tracker.controller;

import com.expense.tracker.model.Category;
import com.expense.tracker.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping
    public List<Category> getAll(Authentication auth) {
        return categoryService.getAllCategories(auth.getName());
    }

    @PostMapping
    public Category add(Authentication auth, @RequestBody Category category) {
        return categoryService.addCategory(auth.getName(), category);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        categoryService.deleteCategory(id);
    }
    
}