package com.example.contractsystem.Service;

import com.example.contractsystem.ApiResponse.ApiException;
import com.example.contractsystem.Model.Expense;
import com.example.contractsystem.Model.Stage;
import com.example.contractsystem.Repository.ExpenseRepository;
import com.example.contractsystem.Repository.ProjectRepository;
import com.example.contractsystem.Repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;

    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public void addExpense(Expense expense) {
        if (expense.getAmount() <= 0) {
            throw new ApiException("Amount must be positive");
        }
        if (expense.getDate().isAfter(LocalDate.now())) {
            throw new ApiException("Expense date cannot be in the future");
        }
        if (projectRepository.findProjectById(expense.getProjectId()) == null) {
            throw new ApiException("Project does not exist");
        }
        if (stageRepository.findStageById(expense.getStageId()) == null) {
            throw new ApiException("Stage does not exist");
        }

        Stage stage = stageRepository.findStageById(expense.getStageId());
        stage.setActualCost(expense.getAmount());

        expenseRepository.save(expense);
        stageRepository.save(stage);
    }

    public void updateExpense(Integer expenseId, Expense expense) {
        if (expenseRepository.findExpenseById(expenseId) == null) throw new ApiException("Expense not found");

        Expense oldExpense = expenseRepository.findExpenseById(expenseId);
        oldExpense.setAmount(expense.getAmount());
        oldExpense.setDate(expense.getDate());
        oldExpense.setDescription(expense.getDescription());
        oldExpense.setProjectId(expense.getProjectId());
        expenseRepository.save(oldExpense);
    }

    public void deleteExpense(Integer expenseId) {
        if (expenseRepository.findExpenseById(expenseId)==null) throw new ApiException("Expense not found");
        expenseRepository.deleteById(expenseId);
    }




    public List<Expense> getExpensesByProjectId(Integer projectId) {
        return expenseRepository.findByProjectId(projectId);
    }

    public Double getTotalExpensesByProjectId(Integer projectId) {
        Double total = expenseRepository.findTotalExpensesByProjectId(projectId);
        return total != null ? total : 0.0;
    }

    public Double getTotalExpensesByStageId(Integer stageId) {
        Double total = expenseRepository.findTotalExpensesByStageId(stageId);
        return total != null ? total : 0.0;
    }

    public Map<String, Double> calculateMonthlyExpenses(Integer projectId) {

        List<Expense> expenses = expenseRepository.findByProjectId(projectId);

        if (expenses.isEmpty()) throw new ApiException("No expenses found for the given project ID");

        return expenses.stream()
                .collect(Collectors.groupingBy(
                        expense -> expense.getDate().getMonth().toString(),
                        Collectors.summingDouble(Expense::getAmount)
                ));
    }
}
