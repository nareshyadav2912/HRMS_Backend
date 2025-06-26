package com.example.sechay.service;

import com.example.sechay.dto.EmployeeDetailsDto;
import com.example.sechay.dto.HREmployeeDto;
import com.example.sechay.model.Employee;
import com.example.sechay.repo.EmployeeRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Transactional
    public Employee createEmployeeFromHr(HREmployeeDto hrEmployeeDto){
        //String emailRegex="^[a-zA-Z]+\\.([a-zA-Z]+)@company\\.com$\n";
        Employee employee=new Employee();
        employee.setEmpId(hrEmployeeDto.getEmpId());
        //Pattern pattern=Pattern.compile(emailRegex);
        //Matcher matcher=pattern.matcher(hrEmployeeDto.getEmpEmail());
        employee.setEmpEmail(hrEmployeeDto.getEmpEmail());
        employee.setEmpPassword(hrEmployeeDto.getEmpPassword());
        return employeeRepo.save(employee);
    }
    @Transactional
    public Employee updateEmployeeDetails(EmployeeDetailsDto employeeDetailsDto){
        Employee employee=new Employee();
        employee.setEmpFirstName(employeeDetailsDto.getEmpFirstName());
        employee.setEmpLastName(employeeDetailsDto.getEmpLastName());
        employee.setEmpGender(employeeDetailsDto.getEmpGender());
        employee.setEmpContact(employeeDetailsDto.getEmpContact());
        employee.setEmpAddress(employeeDetailsDto.getEmpAddress());
        employee.setEmpNewPassword(encoder.encode(employeeDetailsDto.getEmpNewPassword()));
        employee.setEmpConfirmPassword(encoder.encode(employeeDetailsDto.getEmpConfirmPassword()));
        return employeeRepo.save(employee);
    }
    public List<Employee> getAllEmployees(){
        return employeeRepo.findAll();
    }
    public Employee getEmployeeById(int empId){
        return employeeRepo.findById(empId).orElse(null);
    }
    public Employee addEmployee(HREmployeeDto hrEmployeeDto){
        Employee employee=new Employee();
        //employee.setEmpId(hrEmployeeDto.getEmpId());
        employee.setEmpEmail(hrEmployeeDto.getEmpEmail());
        employee.setEmpPassword(encoder.encode(hrEmployeeDto.getEmpPassword()));
        employee.setEmpRole(hrEmployeeDto.getEmpRole());
        return employeeRepo.save(employee);
    }
    public void deleteEmployee(int empId){
        employeeRepo.deleteById(empId);
    }
    public Employee getProfile(String empEmail) {
        return employeeRepo.findByEmpEmail(empEmail);
    }
    public Employee updateProfile(int empId,EmployeeDetailsDto employeeDetailsDto){
        Employee employee1=employeeRepo.findById(empId).orElseThrow(()->new UsernameNotFoundException("Employee not found"));
        if(employee1==null){
            throw new IllegalArgumentException("Employee Not Found");
        }
        employee1.setEmpFirstName(employeeDetailsDto.getEmpFirstName());
        employee1.setEmpLastName(employeeDetailsDto.getEmpLastName());
        employee1.setEmpGender(employeeDetailsDto.getEmpGender());
        employee1.setEmpContact(employeeDetailsDto.getEmpContact());
        employee1.setEmpAddress(employeeDetailsDto.getEmpAddress());
        employee1.setEmpNewPassword(employeeDetailsDto.getEmpNewPassword());
        employee1.setEmpConfirmPassword(employeeDetailsDto.getEmpConfirmPassword());
        employee1.setEmpPassword(encoder.encode(employeeDetailsDto.getEmpConfirmPassword())) ;

        boolean profileComplete= employeeDetailsDto.getEmpFirstName()!=null && !employeeDetailsDto.getEmpFirstName().isEmpty()
                && employeeDetailsDto.getEmpLastName()!=null && !employeeDetailsDto.getEmpLastName().isEmpty()
                && employeeDetailsDto.getEmpContact()!=null && !employeeDetailsDto.getEmpContact().isEmpty()
                && employeeDetailsDto.getEmpAddress()!=null && !employeeDetailsDto.getEmpAddress().isEmpty()
                && employeeDetailsDto.getEmpGender()!=null && !employeeDetailsDto.getEmpGender().isEmpty()
                && employeeDetailsDto.getEmpNewPassword()!=null && !employeeDetailsDto.getEmpNewPassword().isEmpty()
                && employeeDetailsDto.getEmpConfirmPassword()!=null && !employeeDetailsDto.getEmpConfirmPassword().isEmpty();
        employee1.setProfileComplete(profileComplete);
        return employeeRepo.save(employee1);
    }
}
