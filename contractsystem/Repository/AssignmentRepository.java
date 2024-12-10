package com.example.contractsystem.Repository;

import com.example.contractsystem.Model.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Integer> {

    Assignment findAssignmentById(Integer id);
    Assignment findAssignmentByContractorId(Integer contractorId);

}
