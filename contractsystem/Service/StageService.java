package com.example.contractsystem.Service;

import com.example.contractsystem.ApiResponse.ApiException;
import com.example.contractsystem.Model.Stage;
import com.example.contractsystem.Repository.ContractorRepository;
import com.example.contractsystem.Repository.ProjectRepository;
import com.example.contractsystem.Repository.StageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;
    private final ProjectRepository projectRepository;
    private final ContractorRepository contractorRepository;


    public List<Stage> getAllStages() {
        return stageRepository.findAll();
    }

    public void addStage(Stage stage) {
        if (stage.getEndDate().isBefore(LocalDate.now())) {
            throw new ApiException("End date must be after start date");
        }
        if (projectRepository.findProjectById(stage.getProjectId()) == null) {
            throw new ApiException("Project not found");
        }
        if (contractorRepository.findContractorById(stage.getContractorId()) == null) {
            throw new ApiException("Contractor not found");
        }

        if (projectRepository.findProjectById(stage.getProjectId()).getContractId() != contractorRepository.findContractorById(stage.getContractorId()).getId()) throw new ApiException("Contractor have not been assigned");

        stage.setStatus("Pending");
        stageRepository.save(stage);
    }

    public void updateStage(Integer id, Stage stage) {
        if (stageRepository.findStageById(id) == null) throw new ApiException("Stage not found");

        Stage oldStage = stageRepository.findStageById(id);

        oldStage.setName(stage.getName());
        oldStage.setDescription(stage.getDescription());
        oldStage.setStatus(stage.getStatus());
        oldStage.setProjectId(stage.getProjectId());
        oldStage.setStartDate(stage.getStartDate());
        oldStage.setEndDate(stage.getEndDate());
        oldStage.setExpectedCost(stage.getExpectedCost());
        oldStage.setActualCost(stage.getActualCost());
        stageRepository.save(oldStage);
    }

    public void deleteStage(Integer id) {
        if (stageRepository.findStageById(id) == null) throw new ApiException("Stage not found");
        stageRepository.deleteById(id);
    }


    public void startStage(Integer id) {
        if (stageRepository.findStageById(id) == null) throw new ApiException("Stage not found");

        Stage stage = stageRepository.findStageById(id);

        stage.setStatus("In Progress");
        stageRepository.save(stage);

    }

    public void endStage(Integer id) {
        if (stageRepository.findStageById(id) == null) throw new ApiException("Stage not found");

        Stage stage = stageRepository.findStageById(id);

        stage.setStatus("Completed");
        stageRepository.save(stage);
    }

    public void delayStage(Integer id) {
        if (stageRepository.findStageById(id) == null) throw new ApiException("Stage not found");

        Stage stage = stageRepository.findStageById(id);

        stage.setStatus("Delayed");
        stageRepository.save(stage);
    }

    public List<Stage> getStagesByProjectId(Integer projectId) {
        if (stageRepository.findByProjectId(projectId) == null) throw new ApiException("Stage not found");
        return stageRepository.findByProjectId(projectId);
    }

    public String analyzeRiskForStage(Integer stageId) {
        Stage stage = stageRepository.findStageById(stageId);

        if (stage == null) throw new ApiException("Stage not found");

        if ("Delayed".equalsIgnoreCase(stage.getStatus())) {
            return "High Risk: Stage is already delayed.";
        }

        if (LocalDate.now().isAfter(stage.getEndDate())) {
            return "Medium Risk: Stage is at risk of delay.";
        }

        return "Low Risk: Stage is on track.";
    }

    public List<Stage> getDelayedStages(Integer projectId) {
        return stageRepository.findByProjectId(projectId)
                .stream()
                .filter(stage -> "Delayed".equalsIgnoreCase(stage.getStatus()))
                .toList();
    }


}
