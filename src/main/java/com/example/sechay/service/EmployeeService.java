package com.example.sechay.service;

import com.example.sechay.model.Employee;
import com.example.sechay.repo.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    public EmployeeService(EmployeeRepo employeeRepo) {
        this.employeeRepo = employeeRepo;
    }
    @Autowired
    private final EmployeeRepo employeeRepo;
    PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    @Transactional
    public Employee saveUser(Employee employee){
        employee.setEmpPassword(encoder.encode(employee.getEmpPassword()));
        //System.out.println(user.getPassword());
        return employeeRepo.save(employee);
    }
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }
    public Employee getEmployeeById(int empId){
        return employeeRepo.findById(empId).orElse(null);
    }
    public Employee addEmployee(Employee employee){
        return employeeRepo.save(employee);
    }
    public void deleteEmployee(int empId){
        employeeRepo.deleteById(empId);
    }
    public Employee getProfile(String empEmail) {
        return employeeRepo.findByEmpEmail(empEmail);
    }
    public Employee updateProfile(Employee newEmployee){
        Employee employee1=employeeRepo.findById(newEmployee.getEmpId()).orElse(null);
        if(employee1==null){
            throw new IllegalArgumentException("Employee Not Found");
        }
        employee1.setEmpEmail(newEmployee.getEmpEmail());
        return employeeRepo.save(employee1);
    }
}
