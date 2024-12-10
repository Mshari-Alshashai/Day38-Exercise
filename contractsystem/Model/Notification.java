package com.example.contractsystem.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "User ID is required")
    @Column(nullable = false)
    private Integer userId;

    @NotEmpty(message = "Message is required")
    @Size(max = 500, message = "Message cannot exceed 500 characters")
    @Column(nullable = false, length = 500)
    private String message;

    @Column
    private LocalDateTime timestamp;

    @Column
    private Boolean isRead;
}
