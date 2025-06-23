package com.example.sechay.repo;

import com.example.sechay.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee,Integer> {
    Employee findByEmpEmail(String empEmail);
}
