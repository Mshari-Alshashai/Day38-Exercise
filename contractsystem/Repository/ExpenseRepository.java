package com.example.contractsystem.Repository;

import com.example.contractsystem.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Integer> {

    Expense findExpenseById(Integer id);

    List<Expense> findByProjectId(Integer projectId);

    @Query("select sum(e.amount) from Expense e where e.projectId = :projectId")
    Double findTotalExpensesByProjectId(@Param("projectId") Integer projectId);

    @Query("select sum(e.amount) from Expense e where e.stageId = :stageId")
    Double findTotalExpensesByStageId(@Param("stageId") Integer stageId);
}