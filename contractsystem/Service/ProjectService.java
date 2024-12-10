package com.example.contractsystem.Service;

import com.example.contractsystem.ApiResponse.ApiException;
import com.example.contractsystem.Model.Project;
import com.example.contractsystem.Model.Stage;
import com.example.contractsystem.Repository.ProjectRepository;
import com.example.contractsystem.Repository.StageRepository;
import com.example.contractsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.time.temporal.ChronoUnit.DAYS;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;
    private final UserRepository userRepository;

    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }

    public void addProject(Project project) {
        if (project.getBudget() <= 0) {
            throw new ApiException("Budget must be positive");
        }
        if (project.getEndDate().isBefore(LocalDate.now())) {
            throw new ApiException("End date must be after start date");
        }
        if (userRepository.findUsersById(project.getUserId()) == null) {
            throw new ApiException("User not found");
        }
        project.setStatus("pending");
        projectRepository.save(project);
    }

    public void updateProject(Integer id, Project project) {
        if (projectRepository.findProjectById(id) == null) throw new ApiException("Project not found");

        Project oldProject = projectRepository.findProjectById(id);

        oldProject.setName(project.getName());
        oldProject.setBudget(project.getBudget());
        oldProject.setEndDate(project.getEndDate());
        oldProject.setStartDate(project.getStartDate());
        oldProject.setDescription(project.getDescription());
        oldProject.setStatus(project.getStatus());
        projectRepository.save(oldProject);
    }

    public void deleteProject(Integer id) {
        if (projectRepository.findProjectById(id) == null) throw new ApiException("Project not found");
        projectRepository.deleteById(id);
    }



    public void getProjectById(Integer projectId) {
        if (projectRepository.findProjectById(projectId) == null) throw new ApiException("Project not found");
        projectRepository.findProjectById(projectId);
    }

    public Double calculateProjectProgress(Integer projectId) {
        List<Stage> stages = stageRepository.findByProjectId(projectId);

        if (stages.isEmpty()) throw new ApiException("No stages found for the given project ID");

        double completedStages = stages.stream().filter(stage -> "Completed".equalsIgnoreCase(stage.getStatus())).count();

        return  completedStages / stages.size() * 100;
    }

    public Double predictFinalCost(Integer projectId) {
        List<Stage> stages = stageRepository.findByProjectId(projectId);
        if (stages.isEmpty()) throw new ApiException("No stages found for the given project ID");

        double totalExpectedCost = stages.stream()
                .mapToDouble(Stage::getExpectedCost)
                .sum();

        double actualCostSoFar = stages.stream()
                .filter(stage -> stage.getActualCost() != null)
                .mapToDouble(Stage::getActualCost)
                .sum();

        double progressPercentage = calculateProjectProgress(projectId) / 100;

        return actualCostSoFar + (totalExpectedCost * (1 - progressPercentage));
    }

    public LocalDate predictCompletionDate(Integer projectId) {
        List<Stage> stages = stageRepository.findByProjectId(projectId);
        if (stages.isEmpty()) {
            throw new ApiException("No stages found for the given project ID");
        }

        long totalDays = stages.stream()
                .filter(stage -> "Completed".equalsIgnoreCase(stage.getStatus()))
                .mapToLong(stage -> {
                    long days = DAYS.between(stage.getStartDate(), stage.getEndDate());
                    return days;
                })
                .sum();

        long completedStages = stages.stream()
                .filter(stage -> "Completed".equalsIgnoreCase(stage.getStatus()))
                .count();

        if (completedStages == 0) {
            throw new ApiException("No completed stages to base predictions on.");
        }

        long averageDuration = totalDays / completedStages;

        long remainingStages = stages.size() - completedStages;
        return LocalDate.now().plusDays(remainingStages * averageDuration);
    }

    public Map<String, String> analyzeProjectRisk(Integer projectId) {
        List<Stage> stages = stageRepository.findByProjectId(projectId);
        if (stages.isEmpty()) {
            throw new ApiException("No stages found for the given project ID");
        }

        Map<String, String> risks = new HashMap<>();

        long delayedStagesCount = stages.stream()
                .filter(stage -> "Delayed".equalsIgnoreCase(stage.getStatus()))
                .count();
        if (delayedStagesCount > 0) {
            risks.put("Delays", delayedStagesCount + " stages are delayed.");
        }

        Double totalSpent = stages.stream()
                .filter(stage -> stage.getActualCost() != null)
                .mapToDouble(Stage::getActualCost)
                .sum();
        Double totalBudget = projectRepository.findById(projectId)
                .orElseThrow(() -> new ApiException("Project not found"))
                .getBudget();
        if (totalSpent > totalBudget) {
            risks.put("Budget", "Spending exceeds budget by " + (totalSpent - totalBudget) + " units.");
        }

        if (risks.isEmpty()) {
            risks.put("Status", "No significant risks detected.");
        }

        return risks;
    }


}
