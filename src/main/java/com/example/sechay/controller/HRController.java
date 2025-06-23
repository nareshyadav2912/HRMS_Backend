package com.example.sechay.controller;


import com.example.sechay.model.Employee;
import com.example.sechay.model.LeaveRequest;
import com.example.sechay.model.LeaveStatus;
import com.example.sechay.model.PaySlips;
import com.example.sechay.service.EmployeeService;
import com.example.sechay.service.LeaveService;
import com.example.sechay.service.PaySlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hr")
@PreAuthorize("hasRole('HR')")
public class HRController {

    @Autowired
    EmployeeService employeeService;

    @Autowired
    LeaveService leaveService;

    @Autowired
    PaySlipService paySlipService;

    @PostMapping("/addEmployee")
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee){
        Employee employee1=null;
        try{
            employee1=employeeService.addEmployee(employee);
            return new ResponseEntity<>(employee1, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getAllEmployees")
    public ResponseEntity<List<Employee>> getAllEmployees(){
        return new ResponseEntity<>(employeeService.getAllEmployees(),HttpStatus.OK);
    }
    @GetMapping("/getEmployeeById/{empId}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable int empId){
        Employee employee= employeeService.getEmployeeById(empId);
        if(employee!=null){
            return new ResponseEntity<>(employee,HttpStatus.FOUND);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/deleteEmployee/{empId}")
    public ResponseEntity<String> deleteEmployee(@PathVariable int empId){
        Employee employee= employeeService.getEmployeeById(empId);
        if(employee!=null){
            employeeService.deleteEmployee(empId);
            return new ResponseEntity<>("Successfully Deleted",HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/leaves/allLeaves")
    public ResponseEntity<?> getAllLeaves(){
        List<LeaveRequest> leaveRequestList=leaveService.getAllLeaves();
        if(leaveRequestList.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("No Leave Requests!");
        }
        return ResponseEntity.ok(leaveRequestList);
    }
    @PutMapping("/leaves/{leaveId}/status")
    public ResponseEntity<?> updateLeaveStatus(@PathVariable int leaveId,@RequestParam String status){
        LeaveStatus updatedStatus;
        try{
            updatedStatus=LeaveStatus.valueOf(status.toUpperCase());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Status Value, Use APPROVED/REJECTED");
        }
        boolean updated= leaveService.updateLeaveStatus(leaveId,updatedStatus);
        if(!updated){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Leave Request Not Found");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Leave status updated:"+updatedStatus);
    }
    @GetMapping("/leaves/status")
    public ResponseEntity<?> getLeavesByStatus(@RequestParam String status){
        LeaveStatus parsedStatus;
        try{
            parsedStatus=LeaveStatus.valueOf(status.toUpperCase());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid Status Value!");
        }
        List<LeaveRequest> leaveRequestList=leaveService.getByStatus(parsedStatus);
        if(leaveRequestList.isEmpty()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No requests found for"+parsedStatus);
        }
        return ResponseEntity.status(HttpStatus.FOUND).body(leaveRequestList);
    }

    @PostMapping("/payslips/upload/{empEmail}")
    public ResponseEntity<?> uploadPaySlip(@PathVariable String empEmail, @RequestParam MultipartFile file){
        try{
            PaySlips paySlips=paySlipService.savePaySlip(empEmail,file);
            return ResponseEntity.status(HttpStatus.OK).body("PaySlip uploaded for: "+empEmail);

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading payslip: "+e.getMessage());
        }
    }
}
