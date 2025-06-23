package com.example.sechay.repo;

import com.example.sechay.model.PaySlips;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PaySlipRepo extends JpaRepository<PaySlips,Integer> {
    List<PaySlips> findByEmployeeEmpEmail(String empEmail);
}
