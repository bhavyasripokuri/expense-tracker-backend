package com.expense.tracker.service;

import com.expense.tracker.model.Expense;
import com.expense.tracker.model.User;
import com.expense.tracker.repository.ExpenseRepository;
import com.expense.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private UserRepository userRepository;

    private User getUser(String email) {
        return userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
    }

    public List<Expense> getAllExpenses(String email) {
        return expenseRepository.findByUserEmail(email);
    }

    public List<Expense> getExpensesByMonth(String email, int month, int year) {
        LocalDate start = LocalDate.of(year, month, 1);
        LocalDate end = start.withDayOfMonth(start.lengthOfMonth());
        return expenseRepository.findByUserEmailAndDateBetween(email, start, end);
    }

    public Expense addExpense(String email, Expense expense) {
        expense.setUser(getUser(email));
        return expenseRepository.save(expense);
    }

    public Expense updateExpense(Long id, Expense updated) {
        Expense existing = expenseRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Expense not found: " + id));
        existing.setTitle(updated.getTitle());
        existing.setAmount(updated.getAmount());
        existing.setDate(updated.getDate());
        existing.setNotes(updated.getNotes());
        existing.setCategory(updated.getCategory());
        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id) {
        expenseRepository.deleteById(id);
    }

    public Double getMonthlyTotal(String email, int month, int year) {
        Double total = expenseRepository.sumByUserEmailAndMonthAndYear(email, month, year);
        return total != null ? total : 0.0;
    }
}