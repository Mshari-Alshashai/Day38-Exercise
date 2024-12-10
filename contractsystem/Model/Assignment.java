package com.example.contractsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private Integer userId;

    @NotNull(message = "Contractor ID is required")
    @Column(nullable = false)
    private Integer contractorId;

    @NotNull(message = "Project ID is required")
    @Column(nullable = false)
    private Integer projectId;


    @Pattern(regexp = "pending|accepted|rejected")
    private String status;

}
