package com.example.contractsystem.Repository;

import com.example.contractsystem.Model.Contractor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractorRepository extends JpaRepository<Contractor, Integer> {

    Contractor findContractorById(Integer id);

    List<Contractor> findByExpertise(String expertise);

}