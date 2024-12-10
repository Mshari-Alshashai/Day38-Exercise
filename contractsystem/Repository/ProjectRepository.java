package com.example.contractsystem.Repository;

import com.example.contractsystem.Model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Project findProjectById(Integer id);
    List<Project> findProjectsByUserId(Integer userId);

}
