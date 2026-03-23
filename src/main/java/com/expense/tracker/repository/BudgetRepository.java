package com.expense.tracker.repository;

import com.expense.tracker.model.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BudgetRepository extends JpaRepository<Budget, Long> {
    List<Budget> findByUserEmailAndMonthAndYear(String email, int month, int year);
    List<Budget> findByUserEmail(String email);
}