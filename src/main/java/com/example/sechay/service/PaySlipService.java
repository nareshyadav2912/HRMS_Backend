package com.example.sechay.service;

import com.example.sechay.model.Employee;
import com.example.sechay.model.PaySlips;
import com.example.sechay.repo.EmployeeRepo;
import com.example.sechay.repo.PaySlipRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PaySlipService {

    @Autowired
    PaySlipRepo paySlipRepo;

    @Autowired
    EmployeeRepo employeeRepo;

    public PaySlips savePaySlip(String empEmail, MultipartFile file) throws IOException {
        Employee employee=employeeRepo.findByEmpEmail(empEmail);
        if(employee==null) throw new RuntimeException("Employee Not Found!!");
        PaySlips paySlips=new PaySlips();
        paySlips.setFileName(file.getOriginalFilename());
        paySlips.setContentType(file.getContentType());
        paySlips.setData(file.getBytes());
        paySlips.setUploadDate(LocalDate.now());
        paySlips.setEmployee(employee);
        return paySlipRepo.save(paySlips);
    }
    public List<PaySlips> getPaySlipsForEmployee(String empEmail){
        return paySlipRepo.findByEmployeeEmpEmail(empEmail);
    }
    public Optional<PaySlips> getPaySlipsById(Integer pId){
        return paySlipRepo.findById(pId);
    }
}
