package com.example.contractsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "Name is required")
    @Size(max = 50, message = "Name cannot exceed 50 characters")
    @Column(nullable = false, length = 50,unique = true)
    private String username;

    @NotEmpty(message = "Email is required")
    @Email(message = "Invalid email format")
    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @NotEmpty(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$")
    @Column(nullable = false)
    private String password;

    @NotEmpty(message = "Phone number is required")
    @Pattern(regexp = "^05\\d{8}$", message = "Invalid phone number")
    @Column(nullable = false, unique = true, length = 15)
    private String phoneNumber;

    @NotEmpty(message = "Address is required")
    @Size(max = 250, message = "Address cannot exceed 250 characters")
    @Column(nullable = false, length = 250)
    private String address;
}
