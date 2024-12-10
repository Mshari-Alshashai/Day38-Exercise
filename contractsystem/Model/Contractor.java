package com.example.contractsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Contractor name is required")
    @Size(max = 100, message = "Contractor name cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String name;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^05\\d{8}$", message = "Invalid phone number")
    @Column(length = 15,nullable = false,unique = true)
    private String phoneNumber;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(length = 100,nullable = false, unique = true)
    private String email;

    @NotEmpty(message = "Expertise is required")
    @Size(max = 100, message = "Expertise cannot exceed 100 characters")
    @Column(nullable = false, length = 100)
    private String expertise;
}
