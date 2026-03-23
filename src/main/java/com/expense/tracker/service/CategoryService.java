package com.expense.tracker.service;

import com.expense.tracker.model.Category;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.CategoryRepository;
import com.expense.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Category> getAllCategories(String email) {
        return categoryRepository.findByUserEmail(email);
    }

    public Category addCategory(String email, Category category) {
        category.setUser(getUser(email));
        return categoryRepository.save(category);
    }

    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }
}