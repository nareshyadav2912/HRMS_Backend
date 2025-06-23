package com.example.sechay.controller;


import com.example.sechay.model.Employee;
import com.example.sechay.model.LeaveRequest;
import com.example.sechay.model.PaySlips;
import com.example.sechay.service.EmployeeDetail;
import com.example.sechay.service.EmployeeService;
import com.example.sechay.service.LeaveService;
import com.example.sechay.service.PaySlipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employee")
@PreAuthorize("hasRole('EMPLOYEE')")
public class EmpController {

    private final EmployeeService employeeService;

    @Autowired
    private LeaveService leaveService;

    @Autowired
    private PaySlipService paySlipService;

    @Autowired
    public EmpController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/profile/{empEmail}")
    public ResponseEntity<?> getProfile(@PathVariable String empEmail, Authentication authentication){
        String loggedInEmp=authentication.getName();
        System.out.println("Logged In Emp: " +loggedInEmp);
        if(!empEmail.equalsIgnoreCase(loggedInEmp)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: You can't view other's profile");
        }
        Employee employee= employeeService.getProfile(empEmail);
        if(employee==null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Employee Not Found!");
        }
        return ResponseEntity.ok(employeeService.getProfile(empEmail));
    }

    @PutMapping("/profile/update/{empId}")
    public ResponseEntity<?> updateProfile(@PathVariable int empId,@RequestBody Employee employee,Authentication authentication){
        String loggedInEmp=authentication.getName();
        System.out.println("LoggedInEmp:"+loggedInEmp);
        Employee employee1=employeeService.getProfile(loggedInEmp);
        if(employee1==null || employee1.getEmpId()!=empId){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied: You can't edit other's profile");
        }
        employee.setEmpId(empId);
        Employee updatedEmp=employeeService.updateProfile(employee);
        return ResponseEntity.status(HttpStatus.OK).body("Profile Updated.");
    }

    @PostMapping("leave/apply")
    public ResponseEntity<?> applyLeave(@RequestBody LeaveRequest leaveRequest,Authentication authentication){
        String loggedInEmp=authentication.getName();
        Employee employee=employeeService.getProfile(loggedInEmp);
        if(employee==null || !employee.getEmpEmail().equalsIgnoreCase(loggedInEmp)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        leaveRequest.setEmployee(employee);
        LeaveRequest leaveRequest1=leaveService.submitLeaveRequest(leaveRequest);
        return ResponseEntity.status(HttpStatus.OK).body("Leave Request Submitted");
    }
    @GetMapping("leave/appliedLeaves")
    public ResponseEntity<?> getAppliedLeaves(Authentication authentication){
        String loggedInEmp=authentication.getName();
        Employee employee=employeeService.getProfile(loggedInEmp);
        if (employee==null || !employee.getEmpEmail().equalsIgnoreCase(loggedInEmp)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        List<LeaveRequest> empLeaves=leaveService.getLeaveRequests(employee);
        return ResponseEntity.status(HttpStatus.OK).body(empLeaves);
    }
    @DeleteMapping("leave/{leaveId}")
    public ResponseEntity<?> cancelLeave(@PathVariable int leaveId,Authentication authentication){
        String loggedInEmp=authentication.getName();
        Employee employee=employeeService.getProfile(loggedInEmp);
        if(employee==null || !employee.getEmpEmail().equalsIgnoreCase(loggedInEmp)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        boolean res= leaveService.cancelLeave(leaveId,loggedInEmp);
        if(res){
            return ResponseEntity.status(HttpStatus.OK).body("Leave Cancelled");
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Leave Approval is already in progress!!");
    }

    @GetMapping("/payslips")
    public ResponseEntity<?> getPaySlips(Authentication authentication){
        String loggedInEmp=authentication.getName();
        Employee employee=employeeService.getProfile(loggedInEmp);
        if(employee==null || !employee.getEmpEmail().equalsIgnoreCase(loggedInEmp)){
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
        }
        List<PaySlips> paySlipsList=paySlipService.getPaySlipsForEmployee(loggedInEmp);
        return ResponseEntity.status(HttpStatus.FOUND).body(paySlipsList);
    }

    @GetMapping("/payslips/{pId}")
    public ResponseEntity<?> downloadPaySlip(@PathVariable Integer pId){
        return paySlipService.getPaySlipsById(pId).map(p->ResponseEntity.ok().
                header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=\""+ p.getFileName()+"\"")
                .contentType(MediaType.parseMediaType(p.getContentType()))
                .body(p.getData()))
                .orElse(ResponseEntity.notFound().build());
    }
}
