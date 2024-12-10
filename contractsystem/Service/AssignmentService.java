package com.example.contractsystem.Service;

import com.example.contractsystem.ApiResponse.ApiException;
import com.example.contractsystem.Model.Assignment;
import com.example.contractsystem.Model.Project;
import com.example.contractsystem.Repository.AssignmentRepository;
import com.example.contractsystem.Repository.ContractorRepository;
import com.example.contractsystem.Repository.ProjectRepository;
import com.example.contractsystem.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final UserRepository userRepository;
    private final ContractorRepository contractorRepository;
    private final ProjectRepository projectRepository;


    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    public void addAssignment(Integer userID, Assignment assignment) {
        if (userRepository.findUsersById(userID)==null) throw new ApiException("user not found");
        if (contractorRepository.findContractorById(assignment.getContractorId())==null) throw new ApiException("contractor not found");
        if (projectRepository.findProjectById(assignment.getProjectId())==null) throw new ApiException("project not found");
        assignment.setStatus("pending");
        assignmentRepository.save(assignment);
    }

    public void deleteAssignment(Integer id) {
        if (assignmentRepository.findAssignmentById(id)==null) throw new ApiException("assignment not found");
        assignmentRepository.deleteById(id);
    }



    public void acceptAssignment(Integer id, Integer contractorId) {
        if (assignmentRepository.findAssignmentById(id)==null) throw new ApiException("assignment not found");
        if (assignmentRepository.findAssignmentByContractorId(contractorId)==null) throw new ApiException("contractor not found");

        Project project = projectRepository.findProjectById(assignmentRepository.findAssignmentById(id).getProjectId());
        project.setStatus("active");
        project.setStartDate(LocalDate.now());
        project.setContractId(contractorId);
        projectRepository.save(project);

        Assignment assignment = assignmentRepository.findAssignmentById(id);
        assignment.setStatus("accepted");
        assignmentRepository.save(assignment);

    }

    public void rejectAssignment(Integer id, Integer contractorId) {
        if (assignmentRepository.findAssignmentById(id)==null) throw new ApiException("assignment not found");
        if (assignmentRepository.findAssignmentByContractorId(contractorId)==null) throw new ApiException("contractor not found");

        Assignment assignment = assignmentRepository.findAssignmentById(id);
        assignment.setStatus("rejected");
        assignmentRepository.save(assignment);
    }
}
