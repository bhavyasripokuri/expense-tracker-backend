package com.expense.tracker.service;

import com.expense.tracker.model.Budget;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.BudgetRepository;
import com.expense.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class BudgetService {

    @Autowired
    private BudgetRepository budgetRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Budget> getAllBudgets(String email) {
        return budgetRepository.findByUserEmail(email);
    }

    public List<Budget> getBudgetsByMonthAndYear(String email, int month, int year) {
        return budgetRepository.findByUserEmailAndMonthAndYear(email, month, year);
    }

    public Budget saveBudget(String email, Budget budget) {
        budget.setUser(getUser(email));
        return budgetRepository.save(budget);
    }

    public void deleteBudget(Long id) {
        budgetRepository.deleteById(id);
    }
}