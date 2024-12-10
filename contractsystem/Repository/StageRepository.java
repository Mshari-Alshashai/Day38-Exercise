package com.example.contractsystem.Repository;

import com.example.contractsystem.Model.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {

    Stage findStageById(Integer id);

    List<Stage> findByProjectId(Integer projectId);

}
