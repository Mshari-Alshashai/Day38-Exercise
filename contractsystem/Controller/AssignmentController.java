package com.example.contractsystem.Controller;

import com.example.contractsystem.ApiResponse.ApiResponse;
import com.example.contractsystem.Model.Assignment;
import com.example.contractsystem.Service.AssignmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/assignment")
public class AssignmentController {
    private final AssignmentService assignmentService;

    @GetMapping("/get")
    public ResponseEntity getAllAssignments() {
        return ResponseEntity.status(200).body(assignmentService.getAllAssignments());
    }

    @PostMapping("/add/{userId}")
    public ResponseEntity addAssignment(@PathVariable Integer userId,@RequestBody @Valid Assignment assignment) {
       assignmentService.addAssignment(userId,assignment);
        return ResponseEntity.status(200).body(new ApiResponse("Assignment added successfully"));
    }

    @DeleteMapping("/delete/{assignmentId}")
    public ResponseEntity deleteAssignment(@PathVariable Integer assignmentId) {
        assignmentService.deleteAssignment(assignmentId);
        return ResponseEntity.status(200).body(new ApiResponse("Assignment deleted successfully"));
    }




    @PutMapping("/accept/{assignmentId}/{contractorId}")
    public ResponseEntity acceptAssignment(@PathVariable Integer assignmentId, @PathVariable Integer contractorId) {
        assignmentService.acceptAssignment(assignmentId, contractorId);
        return ResponseEntity.status(200).body(new ApiResponse("Assignment accepted successfully"));
    }

    @PutMapping("/reject/{assignmentId}/{contractorId}")
    public ResponseEntity rejectAssignment(@PathVariable Integer assignmentId, @PathVariable Integer contractorId) {
        assignmentService.rejectAssignment(assignmentId, contractorId);
        return ResponseEntity.status(200).body(new ApiResponse("Assignment rejected successfully"));
    }
}
