package com.expense.tracker.repository;

import com.expense.tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    List<Expense> findByUserEmail(String email);

    List<Expense> findByUserEmailAndDateBetween(String email, LocalDate start, LocalDate end);

    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.user.email = :email AND MONTH(e.date) = :month AND YEAR(e.date) = :year")
    Double sumByUserEmailAndMonthAndYear(@Param("email") String email, @Param("month") int month, @Param("year") int year);

    List<Expense> findByUserEmailAndCategoryId(String email, Long categoryId);
}