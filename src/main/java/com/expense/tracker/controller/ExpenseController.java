package com.expense.tracker.controller;

import com.expense.tracker.model.Expense;
import com.expense.tracker.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService expenseService;

    @GetMapping
    public List<Expense> getAll(
        Authentication auth,
        @RequestParam(required = false) Integer month,
        @RequestParam(required = false) Integer year
    ) {
        String email = auth.getName();
        if (month != null && year != null) {
            return expenseService.getExpensesByMonth(email, month, year);
        }
        return expenseService.getAllExpenses(email);
    }

    @PostMapping
    public Expense add(Authentication auth, @Valid @RequestBody Expense expense) {
        return expenseService.addExpense(auth.getName(), expense);
    }

    @PutMapping("/{id}")
    public Expense update(@PathVariable Long id, @Valid @RequestBody Expense expense) {
        return expenseService.updateExpense(id, expense);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        expenseService.deleteExpense(id);
    }

    @GetMapping("/summary")
    public Double monthlySummary(
        Authentication auth,
        @RequestParam int month,
        @RequestParam int year
    ) {
        return expenseService.getMonthlyTotal(auth.getName(), month, year);
    }
}