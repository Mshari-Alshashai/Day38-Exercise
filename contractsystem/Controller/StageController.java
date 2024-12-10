package com.example.contractsystem.Controller;

import com.example.contractsystem.ApiResponse.ApiResponse;
import com.example.contractsystem.Model.Stage;
import com.example.contractsystem.Service.StageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/stage")
public class StageController {

    private final StageService stageService;

    @GetMapping("/get")
    public ResponseEntity getAllStages() {
        return ResponseEntity.status(200).body(stageService.getAllStages());
    }

    @PostMapping("/add")
    public ResponseEntity addStage(@RequestBody @Valid Stage stage) {
        stageService.addStage(stage);
        return ResponseEntity.status(200).body(new ApiResponse("stage added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateStage(@PathVariable Integer id, @RequestBody @Valid Stage stage) {
        stageService.updateStage(id, stage);
        return ResponseEntity.status(200).body(new ApiResponse("stage updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteStage(@PathVariable Integer id) {
        stageService.deleteStage(id);
        return ResponseEntity.status(200).body(new ApiResponse("stage deleted successfully"));
    }



    @PutMapping("/start/{id}")
    public ResponseEntity startStage(@PathVariable Integer id) {
        stageService.startStage(id);
        return ResponseEntity.status(200).body(new ApiResponse("stage started successfully"));
    }

    @PutMapping("/end/{id}")
    public ResponseEntity endStage(@PathVariable Integer id) {
        stageService.endStage(id);
        return ResponseEntity.status(200).body(new ApiResponse("stage ended successfully"));
    }

    @PutMapping("/delay/{id}")
    public ResponseEntity delayStage(@PathVariable Integer id){
        stageService.delayStage(id);
        return ResponseEntity.status(200).body(new ApiResponse("stage delayed successfully"));
    }

    @GetMapping("/get-by-project-id/{projectId}")
    public ResponseEntity getStagesByProjectId(@PathVariable Integer projectId){
        List<Stage> stages = stageService.getStagesByProjectId(projectId);
        return ResponseEntity.status(200).body(stages);
    }

    @GetMapping("/analyze-risk/{stageId}")
    public ResponseEntity analyzeRiskForStage(@PathVariable Integer stageId){
        String riskAnalysis = stageService.analyzeRiskForStage(stageId);
        return ResponseEntity.status(200).body(new ApiResponse(riskAnalysis));
    }

    @GetMapping("/get-delayed/{projectId}")
    public ResponseEntity getDelayedStages(@PathVariable Integer projectId){
        List<Stage> delayedStages = stageService.getDelayedStages(projectId);
        return ResponseEntity.status(200).body(delayedStages);
    }
}
