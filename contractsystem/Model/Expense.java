package com.example.contractsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Expense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Project ID is required")
    @Column(nullable = false)
    private Integer projectId;

    @NotNull(message = "Stage ID is required")
    @Column(nullable = false)
    private Integer stageId;

    @NotEmpty(message = "Description is required")
    @Size(max = 250, message = "Description cannot exceed 250 characters")
    @Column(nullable = false, length = 250)
    private String description;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be positive")
    @Column(nullable = false)
    private Double amount;

    @NotNull(message = "Date is required")
    @PastOrPresent(message = "Date cannot be in the future")
    @Column(nullable = false)
    private LocalDate date;
}
