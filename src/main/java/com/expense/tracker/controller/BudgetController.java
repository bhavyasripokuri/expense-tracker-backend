package com.expense.tracker.controller;

import com.expense.tracker.model.Budget;
import com.expense.tracker.service.BudgetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/budgets")
public class BudgetController {

    @Autowired
    private BudgetService budgetService;

    @GetMapping
    public List<Budget> getAll(
        Authentication auth,
        @RequestParam(required = false) Integer month,
        @RequestParam(required = false) Integer year
    ) {
        String email = auth.getName();
        if (month != null && year != null) {
            return budgetService.getBudgetsByMonthAndYear(email, month, year);
        }
        return budgetService.getAllBudgets(email);
    }

    @PostMapping
    public Budget save(Authentication auth, @RequestBody Budget budget) {
        return budgetService.saveBudget(auth.getName(), budget);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        budgetService.deleteBudget(id);
    }
}