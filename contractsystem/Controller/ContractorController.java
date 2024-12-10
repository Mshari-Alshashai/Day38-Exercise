package com.example.contractsystem.Controller;

import com.example.contractsystem.ApiResponse.ApiResponse;
import com.example.contractsystem.Model.Contractor;
import com.example.contractsystem.Service.ContractorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contractor")
public class ContractorController {
    private final ContractorService contractorService;

    @GetMapping("/get")
    public ResponseEntity getAllContractors() {
        return ResponseEntity.status(200).body(contractorService.getAllContractors());
    }

    @PostMapping("/add")
    public ResponseEntity addContractor(@RequestBody @Valid Contractor contractor) {
        contractorService.addContractor(contractor);
        return ResponseEntity.status(200).body(new ApiResponse("contractor added successfully"));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity updateContractor(@PathVariable Integer id ,@RequestBody @Valid Contractor contractor) {
        contractorService.updateContractor(id, contractor);
        return ResponseEntity.status(200).body(new ApiResponse("contractor updated successfully"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteContractor(@PathVariable Integer id) {
        contractorService.deleteContractor(id);
        return ResponseEntity.status(200).body(new ApiResponse("contractor deleted successfully"));
    }



    @GetMapping("/get-contractors-by-expertise/{expertise}")
    public ResponseEntity getContractorsByExpertise(@PathVariable String expertise){
        List<Contractor> contractors = contractorService.getContractorsByExpertise(expertise);
        return ResponseEntity.status(200).body(contractors);
    }

    @GetMapping("/report/{id}")
    public ResponseEntity generateUserReport(@PathVariable Integer id){
        Map<String, Object> report = contractorService.generateUserReport(id);
        return ResponseEntity.status(200).body(report);
    }

    @PutMapping("/pause/{contractorId}/{projectId}")
    public ResponseEntity pauseProject(@PathVariable Integer contractorId, @PathVariable Integer projectId ){
        contractorService.pauseProject(contractorId, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("project paused successfully"));
    }

    @PutMapping("/resume/{contractorId}/{projectId}")
    public ResponseEntity resumeProject(@PathVariable Integer contractorId, @PathVariable Integer projectId ){
        contractorService.resumeProject(contractorId, projectId);
        return ResponseEntity.status(200).body(new ApiResponse("project resumed successfully"));
    }
}
