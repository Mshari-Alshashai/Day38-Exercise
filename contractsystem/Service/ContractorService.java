package com.example.contractsystem.Service;

import com.example.contractsystem.ApiResponse.ApiException;
import com.example.contractsystem.Model.Contractor;
import com.example.contractsystem.Model.Notification;
import com.example.contractsystem.Model.Project;
import com.example.contractsystem.Model.User;
import com.example.contractsystem.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ContractorService {

    private final ContractorRepository contractorRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final NotificationRepository notificationRepository;


    public List<Contractor> getAllContractors() {
        return contractorRepository.findAll();
    }

    public void addContractor(Contractor contractor) {
        contractorRepository.save(contractor);
    }

    public void updateContractor(Integer id, Contractor contractor) {
        if (contractorRepository.findContractorById(id) == null) throw new ApiException("Contractor not found");

        Contractor oldContractor = contractorRepository.findContractorById(id);

        oldContractor.setName(contractor.getName());
        oldContractor.setEmail(contractor.getEmail());
        oldContractor.setPhoneNumber(contractor.getPhoneNumber());
        oldContractor.setExpertise(contractor.getExpertise());
        contractorRepository.save(oldContractor);
    }

    public void deleteContractor(Integer id) {
        if (contractorRepository.findContractorById(id) == null) throw new ApiException("Contractor not found");
        contractorRepository.deleteById(id);
    }



    public List<Contractor> getContractorsByExpertise(String expertise) {
        return contractorRepository.findByExpertise(expertise);
    }

    public Map<String, Object> generateUserReport(Integer userId) {
        User user = userRepository.findUsersById(userId);

        Map<String, Object> report = new HashMap<>();
        report.put("username", user.getUsername());
        report.put("email", user.getEmail());

        List<Project> projects = projectRepository.findProjectsByUserId(userId);
        report.put("totalProjects", projects.size());
        report.put("projects", projects);

        List<Notification> notifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        report.put("unreadNotifications", notifications.size());

        return report;
    }

    public void pauseProject(Integer contractorId, Integer projectId) {
        if (contractorRepository.findContractorById(contractorId) == null) throw new ApiException("Contractor not found");
        if (projectRepository.findProjectById(projectId) == null) throw new ApiException("Project not found");
        if (!projectRepository.findProjectById(projectId).getContractId().equals(contractorId)) throw new ApiException("Contractor doesn't belong to this project");
        if (!projectRepository.findProjectById(projectId).getStatus().equals("active")) throw new ApiException("project is not active");

        Project project = projectRepository.findProjectById(projectId);
        project.setStatus("paused");
        projectRepository.save(project);

    }

    public void resumeProject(Integer contractorId, Integer projectId) {
        if (contractorRepository.findContractorById(contractorId) == null) throw new ApiException("Contractor not found");
        if (projectRepository.findProjectById(projectId) == null) throw new ApiException("Project not found");
        if (!projectRepository.findProjectById(projectId).getContractId().equals(contractorId)) throw new ApiException("Contractor doesn't belong to this project");
        if (!projectRepository.findProjectById(projectId).getStatus().equals("paused")) throw new ApiException("Project is already active");

        Project project = projectRepository.findProjectById(projectId);
        project.setStatus("active");
        projectRepository.save(project);
    }
}
