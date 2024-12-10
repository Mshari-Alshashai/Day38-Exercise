package com.example.contractsystem.Controller;

import com.example.contractsystem.ApiResponse.ApiResponse;
import com.example.contractsystem.Model.Expense;
import com.example.contractsystem.Service.ExpenseService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/expense")
public class ExpenseController {
    private final ExpenseService expenseService;

    @GetMapping("/get")
    public ResponseEntity getAllExpenses() {
        return ResponseEntity.status(200).body(expenseService.getAllExpenses());
    }

    @PostMapping("/add")
    public ResponseEntity addExpense(@RequestBody @Valid Expense expense) {
        expenseService.addExpense(expense);
        return ResponseEntity.status(200).body(new ApiResponse("expense added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateExpense(@PathVariable Integer id,@RequestBody @Valid Expense expense) {
        expenseService.updateExpense(id, expense);
        return ResponseEntity.status(200).body(new ApiResponse("expense updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteExpense(@PathVariable Integer id) {
        expenseService.deleteExpense(id);
        return ResponseEntity.status(200).body(new ApiResponse("expense deleted successfully"));
    }



    @GetMapping("/get-by-project-id/{projectId}")
    public ResponseEntity getExpensesByProjectId(@PathVariable Integer projectId){
        List<Expense> expenses = expenseService.getExpensesByProjectId(projectId);
        return ResponseEntity.status(200).body(expenses);
    }

    @GetMapping("/get-total-expenses-project/{projectId}")
    public ResponseEntity getTotalExpensesByProjectId(@PathVariable Integer projectId){
        Double total = expenseService.getTotalExpensesByProjectId(projectId);
        return ResponseEntity.status(200).body(total);
    }

    @GetMapping("/calculate-monthly/{projectId}")
    public ResponseEntity calculateMonthlyExpenses(@PathVariable Integer projectId) {
        Map<String, Double> monthlyExpenses = expenseService.calculateMonthlyExpenses(projectId);
        return ResponseEntity.ok(monthlyExpenses);
    }

    @GetMapping("/get-total-expenses-stage/{stageId}")
    public ResponseEntity getTotalExpensesByStageId(@PathVariable Integer stageId) {
        Double total = expenseService.getTotalExpensesByStageId(stageId);
        return ResponseEntity.status(200).body(total);
    }

}
