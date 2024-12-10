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
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Project ID is required")
    @Column(nullable = false)
    private Integer projectId;

    @NotNull(message = "Contractor ID is required")
    @Column(nullable = false)
    private Integer contractorId;

    @NotEmpty(message = "Stage name is required")
    @Size(max = 100, message = "Stage name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(length = 500)
    private String description;

    @NotNull(message = "Start date is required")
    @Column(nullable = false)
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false)
    private LocalDate endDate;


    @Pattern(regexp = "Pending|In Progress|Completed|Delayed", message = "Invalid status value")
    @Column
    private String status;

    @NotNull(message = "Expected cost is required")
    @Positive(message = "Expected cost must be positive")
    @Column(nullable = false)
    private Double expectedCost;

    @Positive(message = "Actual cost must be positive")
    private Double actualCost;
}
