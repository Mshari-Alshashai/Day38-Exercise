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
public class UserService {

    private final UserRepository userRepository;
    private final ContractorRepository contractorRepository;
    private final StageRepository stageRepository;


    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void addUser(User user) {
        userRepository.save(user);
    }

    public void updateUser(Integer id,User user) {
        if (userRepository.findUsersById(id) == null) throw new ApiException("user not found");

        User oldUser = userRepository.findUsersById(id);

        oldUser.setUsername(user.getUsername());
        oldUser.setEmail(user.getEmail());
        oldUser.setPassword(user.getPassword());
        oldUser.setPhoneNumber(user.getPhoneNumber());
        oldUser.setAddress(user.getAddress());
        userRepository.save(oldUser);
    }

    public void deleteUser(Integer id) {
        if (userRepository.findUsersById(id) == null) throw new ApiException("user not found");
        userRepository.deleteById(id);
    }




    public Contractor getTopContractor() {

        List<Contractor> contractors = contractorRepository.findAll();

        if (contractors.isEmpty()) throw new ApiException("No contractors found");

        return contractors.stream()
                .max((c1, c2) -> {
                    long c1OnTimeCount = stageRepository.findByProjectId(c1.getId())
                            .stream()
                            .filter(stage -> "Completed".equalsIgnoreCase(stage.getStatus()))
                            .count();

                    long c2OnTimeCount = stageRepository.findByProjectId(c2.getId())
                            .stream()
                            .filter(stage -> "Completed".equalsIgnoreCase(stage.getStatus()))
                            .count();

                    return Long.compare(c1OnTimeCount, c2OnTimeCount);
                })
                .orElseThrow(() -> new ApiException("No contractors with completed stages found"));
    }

    public Map<String, Object> generateContractorReport(Integer contractorId) {
        Contractor contractor = contractorRepository.findContractorById(contractorId);

        Map<String, Object> report = new HashMap<>();
        report.put("name", contractor.getName());
        report.put("email", contractor.getEmail());
        report.put("phoneNumber", contractor.getPhoneNumber());
        report.put("expertise", contractor.getExpertise());

        return report;
    }

}
