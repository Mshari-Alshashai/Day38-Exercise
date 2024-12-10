package com.example.contractsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Project name is required")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    @Column(nullable = false, length = 100, unique = true)
    private String name;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(length = 500)
    private String description;

    @Column
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    @Column(nullable = false)
    private LocalDate endDate;

    @NotNull(message = "Budget is required")
    @Positive(message = "Budget must be a positive value")
    @Column(nullable = false)
    private Double budget;

    @Pattern(regexp = "active|paused|pending|completed", message = "Status must be Active, Paused, or Completed")
    @Column
    private String status;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private Integer userId;

    @Column
    private Integer contractId;
}
