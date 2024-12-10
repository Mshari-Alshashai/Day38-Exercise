package com.example.contractsystem.Controller;

import com.example.contractsystem.ApiResponse.ApiResponse;
import com.example.contractsystem.Model.Project;
import com.example.contractsystem.Service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/project")
public class ProjectController {

    private final ProjectService projectService;

    @GetMapping("/get")
    public ResponseEntity getAllProjects() {
        return ResponseEntity.status(200).body(projectService.getAllProjects());
    }

    @PostMapping("/add")
    public ResponseEntity addProject(@RequestBody @Valid Project project) {
        projectService.addProject(project);
        return ResponseEntity.status(200).body(new ApiResponse("project added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateProject(@PathVariable Integer id, @RequestBody @Valid Project project) {
        projectService.updateProject(id, project);
        return ResponseEntity.status(200).body(new ApiResponse("project updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteProject(@PathVariable Integer id) {
        projectService.deleteProject(id);
        return ResponseEntity.status(200).body(new ApiResponse("project deleted successfully"));
    }




    @GetMapping("/get-by-id/{projectId}")
    public ResponseEntity getProjectById(@PathVariable Integer projectId) {
        projectService.getProjectById(projectId);
        return ResponseEntity.status(200).body(new ApiResponse("project found successfully"));
    }

    @GetMapping("/get-progress/{projectId}")
    public ResponseEntity getProjectProgress(@PathVariable Integer projectId) {
        Double progress = projectService.calculateProjectProgress(projectId);
        return ResponseEntity.status(200).body(progress);
    }

    @GetMapping("/predict-cost/{projectId}")
    public ResponseEntity predictFinalCost(@PathVariable Integer projectId) {
        Double predictedCost = projectService.predictFinalCost(projectId);
        return ResponseEntity.status(200).body(predictedCost);
    }

    @GetMapping("/predict-time/{projectId}")
    public ResponseEntity predictCompletionDate(@PathVariable Integer projectId) {
        LocalDate completionDate = projectService.predictCompletionDate(projectId);
        return ResponseEntity.status(200).body(completionDate);
    }

    @GetMapping("/analyze-risk/{projectId}")
    public ResponseEntity analyzeProjectRisk(@PathVariable Integer projectId) {
        Map<String, String> analyzeRisk = projectService.analyzeProjectRisk(projectId);
        return ResponseEntity.status(200).body(analyzeRisk);
    }



}
